package com.br.estimativadeprojetodesoftware.command.usuario;

import com.br.estimativadeprojetodesoftware.command.ProjetoCommand;
import com.br.estimativadeprojetodesoftware.model.Usuario;
import com.br.estimativadeprojetodesoftware.presenter.PrincipalPresenter;
import com.br.estimativadeprojetodesoftware.presenter.usuario.UsuarioPresenter;
import com.br.estimativadeprojetodesoftware.presenter.helpers.WindowManager;
import com.br.estimativadeprojetodesoftware.singleton.UsuarioLogadoSingleton;
import com.br.estimativadeprojetodesoftware.view.usuario.UsuarioView;
import com.br.estimativadeprojetodesoftware.service.BarraService;
import java.awt.BorderLayout;
import java.util.Map;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 *
 * @author tetzner
 */
public class UsuarioCommand implements ProjetoCommand {

    private final PrincipalPresenter principalPresenter;
    private final JDesktopPane desktop;
    private final Map<String, ProjetoCommand> comandos;

    public UsuarioCommand(PrincipalPresenter principalPresenter, JDesktopPane desktop, Map<String, ProjetoCommand> comandos) {
        this.principalPresenter = principalPresenter;
        this.desktop = desktop;
        this.comandos = comandos;
    }

    @Override
    public void execute() {
        String tituloJanela = "Usuário";
        WindowManager windowManager = WindowManager.getInstance();

        if (windowManager.isFrameAberto(tituloJanela)) {

            windowManager.bringToFront(tituloJanela);

            try {
                for (JInternalFrame frame : desktop.getAllFrames()) {
                    if (frame.getTitle().equals(tituloJanela) && frame.isMaximum()) {

                        frame.setMaximum(false);
                        frame.setLocation((desktop.getWidth() - frame.getWidth()) / 2,
                                (desktop.getHeight() - frame.getHeight()) / 2);
                    }
                }
            } catch (Exception ignored) {
            }
        } else {

            UsuarioPresenter usuarioPresenter = new UsuarioPresenter(principalPresenter);
            UsuarioView visualizacaoUsuarioView = usuarioPresenter.getView();
            visualizacaoUsuarioView.setTitle(tituloJanela);
            visualizacaoUsuarioView.setSize(564, 385);
            int x = (desktop.getWidth() - visualizacaoUsuarioView.getWidth()) / 2;
            int y = (desktop.getHeight() - visualizacaoUsuarioView.getHeight()) / 2;
            visualizacaoUsuarioView.setLocation(x, y);
            desktop.add(visualizacaoUsuarioView);
            visualizacaoUsuarioView.setVisible(true);

            try {
                if (visualizacaoUsuarioView.isMaximum()) {
                    visualizacaoUsuarioView.setMaximum(false);
                }
            } catch (Exception ignored) {
            }
        }
    }

}
