package application;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Aluno;

public class Main {

    public static void main(String[] args) {
        List<Aluno> listaAlunos = new ArrayList<>();
        JFrame frame = new JFrame("Cadastro de Alunos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[][] {},
                new String[] { "Matricula", "Nome", "Idade", "Data de Nascimento", "Telefone", "CPF" });

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Adicionar Aluno");
        JButton showButton = new JButton("Mostrar Alunos");
        JButton saveButton = new JButton("Salvar");
        JButton removeButton = new JButton("Remover Aluno");

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Aluno aluno = criarAluno();
                if (aluno != null) {
                    if (listaAlunos.contains(aluno)) {
                        JOptionPane.showMessageDialog(frame, "Matricula já existente!");
                    } else {
                        listaAlunos.add(aluno);
                        Object[] row = {
                                aluno.getMatricula(),
                                aluno.getNome(),
                                aluno.getIdade(),
                                formatarData(aluno.getDataNascimento()),
                                aluno.getTelefone(),
                                formatarCPF(aluno.getCpf())
                        };
                        tableModel.addRow(row);
                    }
                }
            }
        });

        showButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarAlunos(listaAlunos);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    salvarEmCSV(listaAlunos);
                    JOptionPane.showMessageDialog(frame, "Registros salvos em CSV com sucesso!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao salvar registros em CSV.");
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String matriculaStr = JOptionPane.showInputDialog(frame, "Digite a matrícula do aluno a ser removido:");
                if (matriculaStr != null && !matriculaStr.isEmpty()) {
                    int matricula = Integer.parseInt(matriculaStr);
                    Aluno alunoRemover = null;
                    for (Aluno aluno : listaAlunos) {
                        if (aluno.getMatricula() == matricula) {
                            alunoRemover = aluno;
                            break;
                        }
                    }
                    if (alunoRemover != null) {
                        listaAlunos.remove(alunoRemover);
                        for (int i = 0; i < tableModel.getRowCount(); i++) {
                            int matriculaTabela = (int) tableModel.getValueAt(i, 0);
                            if (matriculaTabela == matricula) {
                                tableModel.removeRow(i);
                                break;
                            }
                        }
                        JOptionPane.showMessageDialog(frame, "Aluno removido com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Aluno não encontrado!");
                    }
                }
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(showButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(removeButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setSize(600, 400);
        frame.setVisible(true);
    }

    private static Aluno criarAluno() {
        try {
            
            int matricula = Integer.parseInt(JOptionPane.showInputDialog("Matricula:"));
            String nome = JOptionPane.showInputDialog("Nome:");
            int idade = Integer.parseInt(JOptionPane.showInputDialog("Idade:"));
            Date dataNascimento = parsearData(JOptionPane.showInputDialog("Data de Nascimento (dd/mm/yyyy):"));
            String telefone = JOptionPane.showInputDialog("Telefone:");
            String cpf = JOptionPane.showInputDialog("CPF (XXX.XXX.XXX-XX):");

            return new Aluno(matricula, nome, idade, dataNascimento, telefone, cpf);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valor inválido!");
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Data inválida!");
        }

        return null;
    }

    private static Date parsearData(String data) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.parse(data);
    }

    private static String formatarData(Date data) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(data);
    }

    private static String formatarCPF(String cpf) {
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

  private static void mostrarAlunos(List<Aluno> listaAlunos) {
    JFrame frame = new JFrame("Lista de Alunos");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    DefaultTableModel tableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] { "Matricula", "Nome", "Idade", "Data de Nascimento", "Telefone", "CPF" });

    JTable table = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(table);
    frame.add(scrollPane, BorderLayout.CENTER);

    Aluno alunoMaisNovo = null;
    Aluno alunoMaisVelho = null;

    for (Aluno aluno : listaAlunos) {
        Object[] row = {
                aluno.getMatricula(),
                aluno.getNome(),
                aluno.getIdade(),
                formatarData(aluno.getDataNascimento()),
                aluno.getTelefone(),
                formatarCPF(aluno.getCpf())
        };
        tableModel.addRow(row);

        if (alunoMaisNovo == null || aluno.getIdade() < alunoMaisNovo.getIdade()) {
            alunoMaisNovo = aluno;
        }

        if (alunoMaisVelho == null || aluno.getIdade() > alunoMaisVelho.getIdade()) {
            alunoMaisVelho = aluno;
        }
    }

    JLabel quantidadeLabel = new JLabel("Quantidade de Alunos: " + listaAlunos.size());
    JLabel maisNovoLabel = new JLabel("Aluno Mais Novo: " + alunoMaisNovo.getNome() + " (Idade: " + alunoMaisNovo.getIdade() + ")");
    JLabel maisVelhoLabel = new JLabel("Aluno Mais Velho: " + alunoMaisVelho.getNome() + " (Idade: " + alunoMaisVelho.getIdade() + ")");

    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new GridLayout(3, 1));
    infoPanel.add(quantidadeLabel);
    infoPanel.add(maisNovoLabel);
    infoPanel.add(maisVelhoLabel);

    frame.add(infoPanel, BorderLayout.NORTH);

    frame.setSize(600, 400);
    frame.setVisible(true);
}


    private static void salvarEmCSV(List<Aluno> listaAlunos) throws IOException {
        String caminhoArquivo = "C:\\Users\\João Vitor Murada\\Desktop\\UESPI\\project\\data\\ListagemAlunos.csv";

        BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo));
        writer.write("Matricula,Nome,Idade,Data de Nascimento,Telefone,CPF");
        writer.newLine();

        for (Aluno aluno : listaAlunos) {
            String linha = String.format(
                    "%d,%s,%d,%s,%s,%s",
                    aluno.getMatricula(),
                    aluno.getNome(),
                    aluno.getIdade(),
                    formatarData(aluno.getDataNascimento()),
                    aluno.getTelefone(),
                    formatarCPF(aluno.getCpf()));
            writer.write(linha);
            writer.newLine();
        }

        writer.close();
    }
}