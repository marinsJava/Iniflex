import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.text.NumberFormat;
import java.util.Locale;

public class Principal {

    public static void main(String[] args) {

        /*

        Inserir todos os funcionários
        Remover o funcionário "João"
        Imprimir todos os funcionários
        Aumento de 10% no salário
        Agrupar por função em MAP
        Imprimir agrupados por função
        Aniversariantes nos meses 10 e 12
        Funcionário com maior idade
        Ordem alfabética
        Total dos salários
        Salários mínimos por funcionário


        */

        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Maria",    LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"),  "Operador"));
        funcionarios.add(new Funcionario("João",     LocalDate.of(1990, 5,  12), new BigDecimal("2284.38"),  "Operador"));
        funcionarios.add(new Funcionario("Caio",     LocalDate.of(1961, 5,   2), new BigDecimal("9836.14"),  "Coordenador"));
        funcionarios.add(new Funcionario("Miguel",   LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice",    LocalDate.of(1995, 1,   5), new BigDecimal("2234.68"),  "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor",   LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"),  "Operador"));
        funcionarios.add(new Funcionario("Arthur",   LocalDate.of(1993, 3,  31), new BigDecimal("4071.84"),  "Contador"));
        funcionarios.add(new Funcionario("Laura",    LocalDate.of(1994, 7,   8), new BigDecimal("3017.45"),  "Gerente"));
        funcionarios.add(new Funcionario("Heloísa",  LocalDate.of(2003, 5,  24), new BigDecimal("1606.85"),  "Eletricista"));
        funcionarios.add(new Funcionario("Helena",   LocalDate.of(1996, 9,   2), new BigDecimal("2799.93"),  "Gerente"));

        funcionarios.removeIf(f -> f.getNome().equals("João"));
        System.out.println("---> João removido da lista \n");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);

        System.out.println("---> Lista de Funcionários :");
        for (Funcionario f : funcionarios) {
            System.out.printf("Nome: %-10s | Nascimento: %s | Salário: R$ %s | Função: %s%n",
                    f.getNome(),
                    f.getDataNascimento().format(dtf),
                    nf.format(f.getSalario()),
                    f.getFuncao());
        }

        System.out.println("\n---> Salários atualizados com 10% de aumento :");
        for (Funcionario f : funcionarios) {
            BigDecimal novoSalario = f.getSalario().multiply(new BigDecimal("1.10")).setScale(2, RoundingMode.HALF_UP);
            f.setSalario(novoSalario);
            System.out.printf("Nome: %-10s | Novo Salário: R$ %s%n", f.getNome(), nf.format(f.getSalario()));
        }

        Map<String, List<Funcionario>> porFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        System.out.println("\n---> Funcionários agrupados por função :");
        porFuncao.forEach((funcao, lista) -> {
            System.out.println("\nFunção: " + funcao);
            lista.forEach(f ->
                    System.out.printf("  - Nome: %-10s | Nascimento: %s | Salário: R$ %s%n",
                            f.getNome(),
                            f.getDataNascimento().format(dtf),
                            nf.format(f.getSalario()))
            );
        });

        System.out.println("\n---> Aniversariantes em outubro (10) e dezembro (12) :");
        funcionarios.stream()
                .filter(f -> f.getDataNascimento().getMonthValue() == 10
                          || f.getDataNascimento().getMonthValue() == 12)
                .forEach(f -> System.out.printf("Nome: %-10s | Nascimento: %s%n",
                        f.getNome(), f.getDataNascimento().format(dtf)));

        System.out.println("\n---> Funcionário com maior idade :");
        Funcionario maisVelho = funcionarios.stream()
                .min(Comparator.comparing(f -> f.getDataNascimento()))
                .orElseThrow();
        int idade = Period.between(maisVelho.getDataNascimento(), LocalDate.now()).getYears();
        System.out.printf("Nome: %s | Idade: %d anos%n", maisVelho.getNome(), idade);

        System.out.println("\n---> Funcionários em ordem alfabética :");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(f -> System.out.printf("Nome: %-10s | Função: %s%n", f.getNome(), f.getFuncao()));

        System.out.println("\n---> Total dos salários :");
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.printf("Total: R$ %s%n", nf.format(totalSalarios));

        System.out.println("---> Salários mínimos por funcionário (salário mínimo = R$ 1.212,00) :");
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        funcionarios.forEach(f -> {
            BigDecimal qtdSalarios = f.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.printf("Nome: %-10s | Salários mínimos: %s%n", f.getNome(), nf.format(qtdSalarios));
        });
    }
}
