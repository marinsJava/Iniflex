import java.math.BigDecimal;
import java.time.LocalDate;

/*

Classe: Funcionario
- Atributo: salario (BigDecimal)
- Atributo: funcao (String)

+

Classe: Pessoa
- Atributo: nome (String)
- Atributo: data de nascimento (LocalDate)


*/

public class Funcionario extends Pessoa {
    private BigDecimal salario;
    private String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getFuncao() {
        return funcao;
    }
}
