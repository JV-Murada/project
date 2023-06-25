package model;

import java.util.Date;

public class Aluno implements Comparable<Aluno> {
    private int matricula;
    private String nome;
    private int idade;
    private Date dataNascimento;
    private String telefone;
    private String cpf;

    /**
     * Construtor vazio
     */
    public Aluno() {

    }

    /**
     * Construtor da classe Aluno
     */
    public Aluno(int matricula, String nome, int idade, Date dataNascimento, String telefone, String cpf) {
        this.matricula = matricula;
        this.nome = nome;
        this.idade = idade;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.cpf = cpf;
    }

    @Override
    public int compareTo(Aluno aluno) {
        return Integer.compare(this.idade, aluno.idade);
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}