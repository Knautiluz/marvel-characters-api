package net.knautiluz.marvel.matchers;

import net.knautiluz.marvel.models.character.Character;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

public final class CharacterMatchers {

    private CharacterMatchers() {}

    public static NameIs nameIs(String name) {
        return new NameIs(name);
    }

    public static NameStartWith nameStartWith(String name) {
        return new NameStartWith(name);
    }

    public static ModifiedSince modifiedSince(Date date) {
        return new ModifiedSince(date);
    }

    public static IdEquals idEquals(int id) {
        return new IdEquals(id);
    }

    static class NameIs implements Specification<Character> {

        private String name;

        public NameIs(String name) {
            this.name = name;
        }

        @Override
        public Predicate toPredicate(Root<Character> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            if(StringUtils.isEmpty(name)) {
                //no filtering
                return cb.isTrue(cb.literal(true));
            } else {
                return cb.equal(root.get("name"), name);
            }
        }
    }

    static class NameStartWith implements Specification<Character> {

        private String name;

        public NameStartWith(String name) {
            this.name = name;
        }

        @Override
        public Predicate toPredicate(Root<Character> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            if(StringUtils.isEmpty(name)) {
                //no filtering
                return cb.isTrue(cb.literal(true));
            } else {
                return cb.like(root.get("name"), name+"%");
            }
        }
    }

    static class ModifiedSince implements Specification<Character> {

        private Date date;

        public ModifiedSince(Date date) {
            this.date = date;
        }

        @Override
        public Predicate toPredicate(Root<Character> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            if(date == null) {
                //no filtering
                return cb.isTrue(cb.literal(true));
            } else {
                return cb.greaterThan(root.get("modified"), date);
            }
        }
    }

    static class IdEquals implements Specification<Character> {

        private int id;

        public IdEquals(int id) {
            this.id = id;
        }

        @Override
        public Predicate toPredicate(Root<Character> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            return cb.equal(root.get("id"), id);
        }
    }

    //TODO não será possivel implementar o matcher antes de criar o endpoint characters/{characterId}/comics pois é necessário o id da comic
    static class AppearsInComics implements Specification<Character> {

        private List<Integer> comicsIds;

        public AppearsInComics(List<Integer> comicsIds) {
            this.comicsIds = comicsIds;
        }

        @Override
        public Predicate toPredicate(Root<Character> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            if(comicsIds == null) {
                //no filtering
                return cb.isTrue(cb.literal(true));
            } else {
                return root.join("comics").get("representationId").in(comicsIds);
            }
        }
    }

}