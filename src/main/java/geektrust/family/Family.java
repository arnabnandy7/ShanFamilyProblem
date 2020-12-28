package geektrust.family;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author Arnab Nandy
 */
public class Family {
    public static final String NOT_DEFINED = "U_N_K_N_O_W_N";

    private Map<String, Member> members = new HashMap<>();

    public Family(Consumer<Family> populateFn) {
        this.addMember(NOT_DEFINED, null);
        populateFn.accept(this);
    }

    public Member addMember(String name, Gender gender) {
        return Optional
                .ofNullable(members.get(name))
                .orElseGet(() -> {
                    final Member newMember = new Member(name, gender);
                    members.put(name, newMember);
                    return newMember;
                });
    }

    public Member get(String name) {
        return Optional
                .ofNullable(members.get(name))
                .orElseThrow(MemberNotFoundInFamilyException::new);
    }

    public class MemberNotFoundInFamilyException extends RuntimeException {

    }

    public class Member {
        private String name;
        private Gender gender;
        private Member spouse;
        private Member mother;
        private Member father;
        private Set<Member> children = new HashSet<>();

        private Member(String name, Gender gender) {
            this.name = name;
            this.gender = gender;
            this.spouse = members.get(NOT_DEFINED);
            this.mother = members.get(NOT_DEFINED);
            this.father = members.get(NOT_DEFINED);
        }

        public String getName() {
            return name;
        }

        public Gender getGender() {
            return gender;
        }

        public Member getMother() {
            return mother;
        }

        public Member getFather() {
            return father;
        }

        public Set<Member> getChildren() {
            return children;
        }

        public Member getSpouse() {
            return spouse;
        }

        public void isSpouseOf(Member member) {
            this.spouse = member;
            member.spouse = this;
        }

        public void isParentOf(Member member) {
            this.children.add(member);
            this.spouse.children.add(member);
            if (this.gender == Gender.MALE) {
                member.father = this;
            } else {
                member.mother = this;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Member)) {
                return false;
            }

            Member that = (Member) o;

            return !(name != null ? !name.equals(that.name) : that.name != null);
        }

        @Override
        public int hashCode() {
            return name != null ? name.hashCode() : 0;
        }

        @Override
        public String toString() {
            return this.getName();
        }
    }
}
