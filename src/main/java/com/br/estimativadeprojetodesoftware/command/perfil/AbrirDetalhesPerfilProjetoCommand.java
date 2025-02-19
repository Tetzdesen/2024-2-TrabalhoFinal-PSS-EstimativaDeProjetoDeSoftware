package com.br.estimativadeprojetodesoftware.command.perfil;

import javax.swing.JDesktopPane;

import com.br.estimativadeprojetodesoftware.command.ProjetoCommand;
import com.br.estimativadeprojetodesoftware.presenter.helpers.WindowManager;
import com.br.estimativadeprojetodesoftware.presenter.perfil.ManterPerfilPresenter;
import com.br.estimativadeprojetodesoftware.repository.PerfilRepositoryMock;
import com.br.estimativadeprojetodesoftware.view.perfil.ManterPerfilView;

public class AbrirDetalhesPerfilProjetoCommand implements ProjetoCommand {
    private final JDesktopPane desktop;
    private final PerfilRepositoryMock repository;

    public AbrirDetalhesPerfilProjetoCommand(JDesktopPane desktop, PerfilRepositoryMock repository) {
        this.desktop = desktop;
        this.repository = repository;
    }


    @Override
    public void execute() {
        String tituloJanela = "Detalhes do Perfil";
        WindowManager windowManager = WindowManager.getInstance();

        if (windowManager.isFrameAberto(tituloJanela)) {
            windowManager.bringToFront(tituloJanela);
        } else {
            ManterPerfilView manterPerfilView = new ManterPerfilView(desktop);
            new ManterPerfilPresenter(manterPerfilView, repository);
            manterPerfilView.setTitle(tituloJanela);
            desktop.add(manterPerfilView);
            manterPerfilView.setVisible(true);
            try {
                manterPerfilView.setMaximum(true);
            } catch (Exception ignored) {
            }
        }
    }

}
