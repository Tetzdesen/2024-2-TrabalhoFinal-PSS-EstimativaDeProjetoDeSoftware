package com.br.estimativadeprojetodesoftware.presenter.usuario;

import com.br.estimativadeprojetodesoftware.presenter.usuario.CadastroUsuarioPresenter;
import com.br.estimativadeprojetodesoftware.command.MostrarMensagemProjetoCommand;
import com.br.estimativadeprojetodesoftware.model.Usuario;
import com.br.estimativadeprojetodesoftware.presenter.PrincipalPresenter;
import com.br.estimativadeprojetodesoftware.presenter.helpers.WindowManager;
import com.br.estimativadeprojetodesoftware.repository.PerfilRepositoryMock;
import com.br.estimativadeprojetodesoftware.repository.ProjetoRepositoryMock;
import com.br.estimativadeprojetodesoftware.repository.UsuarioRepositoryMock;
import com.br.estimativadeprojetodesoftware.service.IconService;
import com.br.estimativadeprojetodesoftware.singleton.UsuarioLogadoSingleton;
import com.br.estimativadeprojetodesoftware.view.LoginView;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

/**
 *
 * @author tetzner
 */
public class LoginPresenter {

    private LoginView view;
    private ProjetoRepositoryMock repositoryProjeto;
    private UsuarioRepositoryMock repositoryUsuario;
    private UsuarioLogadoSingleton usuarioLogado;

    public LoginPresenter(ProjetoRepositoryMock repositoryProjeto, UsuarioRepositoryMock repositoryUsuario) {
        this.view = new LoginView(null, true);
        this.repositoryProjeto = repositoryProjeto;
        this.repositoryUsuario = repositoryUsuario;
        usuarioLogado = UsuarioLogadoSingleton.getInstancia();
        configuraView();
    }

    private void configuraView() {
       // view.setSize(546, 450);
       // view.setResizable(false);

        view.setLocationRelativeTo(null);
        configuraListerns();
        view.setVisible(true);
    }

    private void configuraListerns() {
        view.getBtnEntrar().addActionListener(e -> {
            try {
                efetuarLogin();
            } catch (Exception ex) {
                new MostrarMensagemProjetoCommand(ex.getMessage()).execute();
            }
        });
        
        view.getBtnExibirSenha().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                view.getBtnExibirSenha().setIcon(IconService.getIcon("olho-exibido"));
                view.getTxtSenha().setEchoChar('\0');
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                view.getBtnExibirSenha().setIcon(IconService.getIcon("olho"));
                view.getTxtSenha().setEchoChar('*');
            }
        });
        
        view.getBtnCadastrar().addActionListener(e -> {
            // view.dispose();
            new CadastroUsuarioPresenter(repositoryUsuario);
        });
    }

    private void efetuarLogin() throws Exception {
        String email = view.getTxtEmail().getText();
        String senha = new String(view.getTxtSenha().getPassword());

        if (camposInvalidos(email, senha)) {
            throw new IllegalArgumentException("Os campos de nome e senha não podem estar vazios");
        }

        Usuario usuario = repositoryUsuario.getUsuarioPorEmail(email);

        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado!");
        }

        if (usuario.getSenha().equals(senha)) {
            usuarioLogado.setUsuario(usuario);
            view.dispose();

            SwingUtilities.invokeLater(() -> {
                PrincipalPresenter presenter = new PrincipalPresenter(new ProjetoRepositoryMock(), new UsuarioRepositoryMock(), new PerfilRepositoryMock());
                WindowManager.getInstance().initialize(presenter);
            });

        } else {
            throw new IllegalArgumentException("Senha incorreta");
        }
    }

    public LoginView getView() {
        return view;
    }

    private boolean camposInvalidos(String email, String senha) {
        return email == null || email.trim().isEmpty() || senha == null || senha.trim().isEmpty();
    }

}
