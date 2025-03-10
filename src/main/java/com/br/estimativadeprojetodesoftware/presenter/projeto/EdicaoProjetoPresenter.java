package com.br.estimativadeprojetodesoftware.presenter.projeto;

import com.br.estimativadeprojetodesoftware.command.MostrarMensagemProjetoCommand;
import com.br.estimativadeprojetodesoftware.model.Estimativa;
import com.br.estimativadeprojetodesoftware.model.Perfil;
import com.br.estimativadeprojetodesoftware.model.Projeto;
import com.br.estimativadeprojetodesoftware.model.Usuario;
import com.br.estimativadeprojetodesoftware.repository.ProjetoRepositoryMock;
import com.br.estimativadeprojetodesoftware.singleton.UsuarioLogadoSingleton;
import com.br.estimativadeprojetodesoftware.view.projeto.EdicaoProjetoView;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;

/**
 *
 * @author Hiago Lopes
 */
public class EdicaoProjetoPresenter {

    private EdicaoProjetoView view;
    private ProjetoRepositoryMock repositoryProjeto;
    private Usuario usuario;
    private Map<String, Perfil> perfisSelecionados;
    private Projeto projetoAtual;

    public EdicaoProjetoPresenter(EdicaoProjetoView view, ProjetoRepositoryMock repositoryProjeto, String nomeProjeto) {
        this.view = view;
        this.repositoryProjeto = repositoryProjeto;
        this.usuario = UsuarioLogadoSingleton.getInstancia().getUsuario();
        this.perfisSelecionados = new HashMap<>();
        this.projetoAtual = repositoryProjeto.getProjetoPorNome(nomeProjeto);

        configuraView();
    }

    private void configuraView() {
        view.setModal(true);
        view.setResizable(false);
        view.setLocationRelativeTo(null);

        configuraActionsListerns();
        carregarListaPerfis();
        carregarDadosProjeto();
    }

    private void carregarDadosProjeto() {
        if (projetoAtual == null) {
            return;
        }

        view.getTxtNome().setText(projetoAtual.getNome());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Perfil perfil : projetoAtual.getPerfis()) {
            listModel.addElement(perfil.getNome());
            perfisSelecionados.put(perfil.getNome(), perfil);
        }

        view.getJListPerfis().setModel(listModel);
    }

    private void configuraActionsListerns() {
        view.getBtnCadastrarProjeto().addActionListener(e -> {
            try {
                salvarAlteracoes();
            } catch (Exception ex) {
                exibirMensagem(ex.getMessage());
            }
        });

        view.getBtnCancelar().addActionListener(e -> {
            try {
                view.dispose();
            } catch (Exception ex) {
                exibirMensagem(ex.getMessage());
            }
        });

        view.getBtnAdicionarPerfil().addActionListener(e -> {
            try {
                adicionarPerfil();
            } catch (Exception ex) {
                exibirMensagem(ex.getMessage());
            }
        });

        view.getBtnRemoverPerfil().addActionListener(e -> {
            try {
                removerPerfil();
            } catch (Exception ex) {
                exibirMensagem(ex.getMessage());
            }
        });

    }

    private void salvarAlteracoes() {
        String nomeProjeto = view.getTxtNome().getText().trim();

        if (nomeProjeto.isEmpty()) {
            exibirMensagem("O nome do projeto é obrigatório!");
            return;
        }

        Projeto projetoAtualizado = new Projeto(nomeProjeto, UsuarioLogadoSingleton.getInstancia().getUsuario().getNome());

        ListModel<String> listModel = view.getJListPerfis().getModel();
        for (int i = 0; i < listModel.getSize(); i++) {
            String nomePerfil = listModel.getElementAt(i);
            projetoAtualizado.adicionarPerfil(perfisSelecionados.get(nomePerfil));
        }

        String tiposConcatenados = projetoAtualizado.getPerfis().stream()
                .map(Perfil::getNome)
                .collect(Collectors.joining(", "));

        projetoAtualizado.setTipo(nomeProjeto);

        Map<String, Integer> funcionalidades = combinarFuncionalidades(projetoAtualizado.getPerfis());
        Estimativa estimativa = new Estimativa(UUID.randomUUID(), LocalDateTime.now(), funcionalidades);

        projetoAtualizado.setEstimativa(estimativa);

        // repositoryProjeto.atualizarProjeto(projetoAtualizado);
        exibirMensagem("Projeto atualizado com sucesso!");
    }

    private void carregarListaPerfis() {

        List<Perfil> perfis = usuario.getPerfis();

        DefaultComboBoxModel<String> cmbPerfis = new DefaultComboBoxModel<>();

        for (Perfil perfil : perfis) {
            cmbPerfis.addElement(perfil.getNome());
            perfisSelecionados.put(perfil.getNome(), perfil);
        }

        view.getCbmPerfis().setModel(cmbPerfis);
    }

    private Map<String, Integer> combinarFuncionalidades(List<Perfil> perfis) {
        Map<String, Integer> funcionalidadesCombinadas = new HashMap<>();
        for (Perfil perfil : perfis) {
            perfil.getFuncionalidades().forEach(funcionalidadesCombinadas::putIfAbsent);
        }
        return funcionalidadesCombinadas;
    }

    private void adicionarPerfil() {
        String perfil = (String) view.getCbmPerfis().getSelectedItem();

        if (perfil == null || perfil.isEmpty() || !perfisSelecionados.containsKey(perfil)) {
            exibirMensagem("Selecione um perfil válido!");
            return;
        }

        DefaultListModel<String> model = (DefaultListModel<String>) view.getJListPerfis().getModel();
        if (!model.contains(perfil)) {
            model.addElement(perfil);
            projetoAtual.adicionarPerfil(perfisSelecionados.get(perfil));
        } else {
            exibirMensagem("Este perfil já foi adicionado!");
        }
    }

    private void removerPerfil() {
        int selectedIndex = view.getJListPerfis().getSelectedIndex();
        if (selectedIndex == -1) {
            exibirMensagem("Selecione um perfil para remover!");
            return;
        }

        DefaultListModel<String> model = (DefaultListModel<String>) view.getJListPerfis().getModel();
        String perfilRemovido = model.getElementAt(selectedIndex);
        model.remove(selectedIndex);

        projetoAtual.removerPerfil(perfisSelecionados.get(perfilRemovido));
        exibirMensagem("Perfil removido com sucesso!");
    }

    public EdicaoProjetoView getView() {
        return view;
    }

    public void exibirMensagem(String mensagem) {
        new MostrarMensagemProjetoCommand(mensagem).execute();
    }
}
