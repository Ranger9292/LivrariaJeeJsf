package br.com.caelum.livraria.bean;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.RollbackException;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;

@ManagedBean
@ViewScoped
public class AutorBean {

	private Autor autor = new Autor();
	private ArrayList<Autor> autores = new ArrayList<Autor>();
	private Integer autorId;
	

	
	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public void carregarAutorPelaId() {
		this.autor = new DAO<Autor>(Autor.class).buscaPorId(autorId);
	}

	public Autor getAutor() {
		return autor;
	}

	public String gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());
		if (autor.getId() == null) {
			new DAO<Autor>(Autor.class).adiciona(this.autor);
		}
		new DAO<Autor>(Autor.class).atualiza(this.autor);
		return "livro?faces-redirect=true";

	}

	public ArrayList<Autor> getAutores() {
		this.autores = (ArrayList<Autor>) new DAO<Autor>(Autor.class).listaTodos();
		return autores;
	}

	public void remover(Autor autor) {
		try {
			new DAO<Autor>(Autor.class).remove(autor);
		} catch (RollbackException Exception) {
			FacesContext.getCurrentInstance().addMessage("autor",
					new FacesMessage("Autor não pode ser excluído por estar associado à um Livro."));
		}
	}

	public void carregar(Autor autor) {
		this.autor = autor;
	}
	
	
}