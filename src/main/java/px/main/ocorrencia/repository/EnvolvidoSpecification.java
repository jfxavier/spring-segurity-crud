package px.main.ocorrencia.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import px.main.ocorrencia.modelos.Envolvido;

public final class EnvolvidoSpecification {
	public static Specification<Envolvido> ByField(String valor, String campo) {
		return new Specification<Envolvido>() {
			@Override
			public Predicate toPredicate(Root<Envolvido> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				System.out.println(campo);
				return builder.like(root.get(campo), "%" + valor.trim() + "%");
			}
		};
	}
}
