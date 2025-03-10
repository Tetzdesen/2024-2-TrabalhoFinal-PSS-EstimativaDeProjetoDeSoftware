package com.br.estimativadeprojetodesoftware.view.usuario;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class PrincipalUsuarioView extends javax.swing.JFrame {

    public PrincipalUsuarioView() {
        initComponents();
    }

    public JButton getBtnAutenticarPorSenha() {
        return btnAutenticarPorSenha;
    }

    public JButton getBtnCadastrar() {
        return btnCadastrar;
    }

    private void initComponents() {
        btnAutenticarPorSenha = new javax.swing.JButton();
        btnCadastrar = new javax.swing.JButton();
        lblDescricao = new javax.swing.JLabel();
        lblPerguntaCadastro = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Estimativa de Projetos de Software");

        btnAutenticarPorSenha.setText("Autenticar por Senha");
        btnCadastrar.setText("Cadastrar");

        btnAutenticarPorSenha.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 14));
        btnCadastrar.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 16));

        btnAutenticarPorSenha.setBackground(new Color(255, 255, 255));
        btnCadastrar.setBackground(new Color(255, 255, 255));

        btnAutenticarPorSenha.setForeground(Color.BLACK);
        btnCadastrar.setForeground(Color.BLACK);

        int padding = 8; 
        btnAutenticarPorSenha.setBorder(new CompoundBorder(
                new LineBorder(Color.BLACK, 3, true), // Borda branca
                new EmptyBorder(padding, padding, padding, padding) // Espaçamento entre a borda e o texto
        ));
        btnCadastrar.setBorder(new CompoundBorder(
                new LineBorder(Color.BLACK, 3, true), // Borda branca
                new EmptyBorder(padding, padding, padding, padding) // Espaçamento entre a borda e o texto
        ));

        btnAutenticarPorSenha.setFocusPainted(false);
        btnCadastrar.setFocusPainted(false);

        addButtonHoverEffect(btnAutenticarPorSenha);
        addButtonHoverEffect(btnCadastrar);

        lblDescricao.setText("<html><center><b>Estimativa de Projetos de Software</b><br>" +
                             "Este sistema ajuda desenvolvedores a calcular o tempo e o custo de criação de aplicativos,<br>" +
                             "com base em funcionalidades específicas e plataformas escolhidas.</center></html>");
        lblDescricao.setFont(new java.awt.Font("Segoe UI", 0, 16));
        lblDescricao.setForeground(Color.BLACK);

        lblPerguntaCadastro.setText("<html><center><b>Deseja se cadastrar?</b></center></html>");
        lblPerguntaCadastro.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 16));
        lblPerguntaCadastro.setForeground(Color.BLACK);

        setLayout(new BorderLayout());

        JPanel painelFundo = new JPanel(new GridBagLayout());
        painelFundo.setBackground(new Color(255, 255, 255));
        painelFundo.setBorder(new LineBorder(Color.BLACK, 2, true));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        painelFundo.add(lblDescricao, gbc);
        gbc.gridy++;
      
        painelFundo.add(btnAutenticarPorSenha, gbc);
        gbc.gridy++;
        painelFundo.add(lblPerguntaCadastro, gbc);
        gbc.gridy++;
        painelFundo.add(btnCadastrar, gbc);

        add(painelFundo, BorderLayout.CENTER);

        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addButtonHoverEffect(JButton button) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 255, 255));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 255, 255));
            }
        });
    }

    private javax.swing.JButton btnAutenticarPorSenha;
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JLabel lblDescricao;
    private javax.swing.JLabel lblPerguntaCadastro;
}
