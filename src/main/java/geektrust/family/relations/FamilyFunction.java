package geektrust.family.relations;

import geektrust.family.Family;
import geektrust.family.Gender;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static geektrust.family.Gender.FEMALE;
import static geektrust.family.Gender.MALE;

/**
 * @author Arnab Nandy
 */
public class FamilyFunction {
    public static Stream<Family.Member> mother(Stream<Family.Member> members) {
        return members
                .map(Family.Member::getMother);
    }

    public static Stream<Family.Member> father(Stream<Family.Member> members) {
        return members
                .map(Family.Member::getFather);
    }

    public static Stream<Family.Member> spouse(Stream<Family.Member> members) {
        return members
                .map(Family.Member::getSpouse);
    }

    public static Stream<Family.Member> children(Stream<Family.Member> members) {
        return members
                .map(Family.Member::getChildren)
                .flatMap(Collection::stream);
    }

    public static Stream<Family.Member> siblings(Stream<Family.Member> members) {
        return members
                .map((member) -> {
                    final Set<Family.Member> children = new HashSet<>(member.getFather().getChildren());
                    children.remove(member);
                    return children;
                })
                .flatMap(Collection::stream);
    }

    public static Stream<Family.Member> sons(Stream<Family.Member> members) {
        return all(MALE, children(members));
    }

    public static Stream<Family.Member> daughters(Stream<Family.Member> members) {
        return all(FEMALE, children(members));
    }

    public static Stream<Family.Member> brothers(Stream<Family.Member> members) {
        return all(MALE, siblings(members));
    }

    public static Stream<Family.Member> sisters(Stream<Family.Member> members) {
        return all(FEMALE, siblings(members));
    }

    private static Stream<Family.Member> all(Gender gender, Stream<Family.Member> members) {
        return members
                .filter(sibling -> sibling.getGender() == gender);
    }
}
