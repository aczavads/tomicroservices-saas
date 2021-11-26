package br.otimizes.tomicroservices.model.projeto;

import br.otimizes.tomicroservices.model.usuário.Usuario;
import org.apache.lucene.search.Query;
import org.hibernate.search.Search;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.hibernate.search.jpa.Search.getFullTextEntityManager;

@Service
public class ManterProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private EntityManager entityManager;

    public Projeto inserir (Projeto novoProjeto) {
        if(projetoRepository.findById(novoProjeto.getId()).isPresent()) {
            throw new RuntimeException("O projeto já existe!");
        }

        return projetoRepository.save(novoProjeto);
    }

    public Projeto editar (Projeto editarProjeto) {
        Projeto projetoEditado = projetoRepository.findById(editarProjeto.getId())
                         .orElseThrow(()-> { throw new RuntimeException("O projeto não encontrado!");});

        projetoEditado.setNome(editarProjeto.getNome());
        projetoEditado.setIdentificador(editarProjeto.getIdentificador());
        projetoEditado.setParticipantes(editarProjeto.getParticipantes());
        projetoEditado.setProprietario(editarProjeto.getProprietario());

        return projetoRepository.save(projetoEditado);

    }

    public void excluir (String id) {
        if(!projetoRepository.findById(id).isPresent()) {
            throw new RuntimeException("O projeto com este id não existe!");
        }
        projetoRepository.deleteById(id);
    }

    public List<Projeto> encontrarTodos() {
        return this.projetoRepository.findAll();
    }

    public Projeto encontrar(String id) {
        Optional<Projeto> projetoEncontrado = projetoRepository.findById(id);
        if(!projetoEncontrado.isPresent()) {
            throw new RuntimeException("O projeto não existe!");
        }
        return projetoEncontrado.get();
    }

    public List<Projeto> encontrarTodos(String termoDeBusca) {

        FullTextEntityManager fullTextEntityManager = getFullTextEntityManager(entityManager);

        QueryBuilder qb = fullTextEntityManager
                .getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Projeto.class)
                .get();

        Query projetosQuery = qb.keyword()
                .onFields("nome","identificador")
                .matching(termoDeBusca)
                .createQuery();

        FullTextQuery fullTextQuery = fullTextEntityManager
                .createFullTextQuery(projetosQuery, Projeto.class);
        return (List<Projeto>) fullTextQuery.getResultList();
    }
}
