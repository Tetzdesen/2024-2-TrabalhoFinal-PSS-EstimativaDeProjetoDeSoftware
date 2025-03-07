package com.br.estimativadeprojetodesoftware.command.usuario;

import com.br.estimativadeprojetodesoftware.command.ProjetoCommand;
import com.br.estimativadeprojetodesoftware.presenter.PrincipalPresenter;
import com.br.estimativadeprojetodesoftware.presenter.usuario.ManterUsuarioPresenter;
import com.br.estimativadeprojetodesoftware.presenter.helpers.WindowManager;
import com.br.estimativadeprojetodesoftware.view.usuario.ManterUsuarioView;
import java.util.Map;
import javax.swing.JDesktopPane;

/**
 *
 * @author tetzner
 */
public class ManterUsuarioCommand implements ProjetoCommand {

    private final PrincipalPresenter principalPresenter;
    private final JDesktopPane desktop;

    public ManterUsuarioCommand(PrincipalPresenter principalPresenter, JDesktopPane desktop) {
        this.principalPresenter = principalPresenter;
        this.desktop = desktop;
    }

    @Override
    public void execute() {
        String tituloJanela = "Usuário";
        WindowManager windowManager = WindowManager.getInstance();

        if (windowManager.isDialogAberto(tituloJanela)) {

            windowManager.bringToFront(tituloJanela);

        } else {

            ManterUsuarioView view = new ManterUsuarioView();
            new ManterUsuarioPresenter(view);

            view.setSize(desktop.getSize()); 
            view.setLocationRelativeTo(null); 

            windowManager.registrarDialog(tituloJanela, view);

            view.setVisible(true);

            view.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    windowManager.fechar(tituloJanela);
                }
            });
        }
    }

}
