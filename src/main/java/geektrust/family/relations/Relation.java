package geektrust.family.relations;

import geektrust.family.Family;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import static geektrust.family.relations.FamilyFunction.brothers;
import static geektrust.family.relations.FamilyFunction.children;
import static geektrust.family.relations.FamilyFunction.daughters;
import static geektrust.family.relations.FamilyFunction.father;
import static geektrust.family.relations.FamilyFunction.mother;
import static geektrust.family.relations.FamilyFunction.siblings;
import static geektrust.family.relations.FamilyFunction.sisters;
import static geektrust.family.relations.FamilyFunction.sons;
import static geektrust.family.relations.FamilyFunction.spouse;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.of;

/**
 * @author Arnab Nandy
 */
public enum Relation {
    MOTHER(member -> mother(of(member))),
    FATHER(member -> father(of(member))),
    SPOUSE(member -> spouse(of(member))),
    BROTHER(member -> brothers(of(member))),
    SISTER(member -> sisters(of(member))),
    SON(member -> sons(of(member))),
    DAUGHTER(member -> daughters(of(member))),
    CHILDREN(member -> children(of(member))),

    GRANDSON(member -> SON.to(children(of(member)))),
    GRANDDAUGHTER(member -> DAUGHTER.to(children(of(member)))),
    COUSIN(member -> union(
            CHILDREN.to(siblings(father(of(member)))),
            CHILDREN.to(siblings(mother(of(member)))))),
    BROTHER_IN_LAW(member -> union(
            BROTHER.to(spouse(of(member))),
            SPOUSE.to(sisters(of(member))))),
    SISTER_IN_LAW(member -> union(
    		SISTER.to(spouse(of(member))),
            SPOUSE.to(brothers(of(member))))),
    PATERNAL_UNCLE(member -> union(
            BROTHER.to(father(of(member))),
            BROTHER_IN_LAW.to(father(of(member))))),
    MATERNAL_UNCLE(member -> union(
            BROTHER.to(mother(of(member))),
            BROTHER_IN_LAW.to(mother(of(member))))),
    PATERNAL_AUNT(member -> union(
            SISTER.to(father(of(member))))),
    MATERNAL_AUNT(member -> union(
            SISTER.to(mother(of(member)))));

    private Function<Family.Member, Stream<Family.Member>> function;

    Relation(Function<Family.Member, Stream<Family.Member>> relationFn) {
        this.function = relationFn;
    }

    public Set<Family.Member> to(Family.Member member) {
        return this.function
                .apply(member)
                .collect(toSet());
    }

    private Stream<Family.Member> to(Stream<Family.Member> members) {
        return members
                .map(this::to)
                .flatMap(Collection::stream);
    }

    private static Stream<Family.Member> union(Stream<Family.Member> ...members) {
        return Arrays.asList(members)
                .stream()
                .reduce(Stream::concat)
                .orElseThrow(RuntimeException::new);

    }
}
