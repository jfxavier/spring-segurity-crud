package px.main.ocorrencia.repository;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import px.main.ocorrencia.modelos.Ocorrencia;

public final class OcorrenciaSpecification {
	public static Specification<Ocorrencia> ByField(String valor,String campo) {
		return new Specification<Ocorrencia>() {
			@Override
			public Predicate toPredicate(Root<Ocorrencia> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				System.out.println(campo);
				return builder.like(root.get(campo),  "%"+valor.trim()+"%");
			}
		};
	}
	public static Specification<Ocorrencia> ByUsuario(String valor) {
		return new Specification<Ocorrencia>() {
			@Override
			public Predicate toPredicate(Root<Ocorrencia> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				System.out.println(valor);
				return builder.like(root.get("usuario"),  "%"+valor.trim()+"%");
			}
		};
	}
	public static Specification<Ocorrencia> ByCodigo(String valor) {
		return new Specification<Ocorrencia>() {
			@Override
			public Predicate toPredicate(Root<Ocorrencia> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				System.out.println(valor);
				return builder.like(root.get("codigo"),  "%"+valor.trim()+"%");
			}
		};
	}
	public static Specification<Ocorrencia> DateGreaterThanOrEqual(Date data, String campo) {
		return new Specification<Ocorrencia>() {
			@Override
			public Predicate toPredicate(Root<Ocorrencia> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.greaterThanOrEqualTo(root.get(campo), data);
			}
		};
	}
	public static Specification<Ocorrencia> DateLessThanOrEqual(Date data, String campo) {
		return new Specification<Ocorrencia>() {
			
			@Override
			public Predicate toPredicate(Root<Ocorrencia> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.lessThanOrEqualTo(root.get(campo), data);
			}
		};
	}
}