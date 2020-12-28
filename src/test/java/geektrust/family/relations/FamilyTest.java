package geektrust.family.relations;

import geektrust.family.Family;
import java.util.Set;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static geektrust.family.Gender.FEMALE;
import static geektrust.family.Gender.MALE;
import static geektrust.family.relations.Relation.BROTHER;
import static geektrust.family.relations.Relation.BROTHER_IN_LAW;
import static geektrust.family.relations.Relation.CHILDREN;
import static geektrust.family.relations.Relation.DAUGHTER;
import static geektrust.family.relations.Relation.FATHER;
import static geektrust.family.relations.Relation.MOTHER;
import static geektrust.family.relations.Relation.SISTER;
import static geektrust.family.relations.Relation.SISTER_IN_LAW;
import static geektrust.family.relations.Relation.SON;
import static geektrust.family.relations.Relation.SPOUSE;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Vivek Mittal
 */
@Test
public class FamilyTest {
    private Family family;

    @BeforeTest
    public void setup() {
        family = new Family(_family_ -> {
            final Family.Member barry = _family_.addMember("BARRY", MALE);
            final Family.Member nora = _family_.addMember("NORA", FEMALE);
            final Family.Member henry = _family_.addMember("HENRY", MALE);
            final Family.Member wally = _family_.addMember("WALLY", MALE);
            final Family.Member iris = _family_.addMember("IRIS", FEMALE);
            final Family.Member patty = _family_.addMember("PATTY", FEMALE);
            final Family.Member sam = _family_.addMember("SAM", MALE);
            final Family.Member danny = _family_.addMember("DANNY", FEMALE);
            
            nora.isSpouseOf(henry);

            nora.isParentOf(barry);
            henry.isParentOf(barry);

            nora.isParentOf(wally);
            henry.isParentOf(wally);

            nora.isParentOf(iris);
            henry.isParentOf(iris);
            nora.isParentOf(sam);
            henry.isParentOf(sam);
            sam.isSpouseOf(danny);
            patty.isSpouseOf(barry);
        });
    }

    public void mother() {
        final Family.Member nora = family.get("NORA");
        final Family.Member barry = family.get("BARRY");

        assertThat(MOTHER.to(barry), is(nora));
    }

    public void father() {
        final Family.Member henry = family.get("HENRY");
        final Family.Member barry = family.get("BARRY");

        assertThat(FATHER.to(barry), is(henry));
    }

    public void brother() {
        final Family.Member wally = family.get("WALLY");
        final Family.Member barry = family.get("BARRY");

        assertThat(BROTHER.to(barry), is(wally));
    }

    public void sister() {
        final Family.Member iris = family.get("IRIS");
        final Family.Member barry = family.get("BARRY");

        assertThat(SISTER.to(barry), is(iris));
    }

    public void spouse() {
        final Family.Member patty = family.get("PATTY");
        final Family.Member barry = family.get("BARRY");

        assertThat(SPOUSE.to(barry), is(patty));
    }

    public void children() {
        final Family.Member henry = family.get("HENRY");
        final Family.Member nora = family.get("NORA");

        final Family.Member barry = family.get("BARRY");
        final Family.Member iris = family.get("IRIS");
        final Family.Member wally = family.get("WALLY");

        assertThat(CHILDREN.to(henry), is(barry, iris, wally));
        assertThat(CHILDREN.to(nora), is(barry, iris, wally));
    }

    public void son() {
        final Family.Member henry = family.get("HENRY");
        final Family.Member nora = family.get("NORA");

        final Family.Member barry = family.get("BARRY");
        final Family.Member wally = family.get("WALLY");

        assertThat(SON.to(henry), is(barry, wally));
        assertThat(SON.to(nora), is(barry, wally));
    }

    public void daughter() {
        final Family.Member henry = family.get("HENRY");
        final Family.Member nora = family.get("NORA");

        final Family.Member iris = family.get("IRIS");

        assertThat(DAUGHTER.to(henry), is(iris));
        assertThat(DAUGHTER.to(nora), is(iris));
    }

    public void sister_in_law() {
        final Family.Member patty = family.get("PATTY");
        final Family.Member iris = family.get("IRIS");

        assertThat(SISTER_IN_LAW.to(patty), is(iris));
    }

    public void brother_in_law() {
        final Family.Member patty = family.get("PATTY");
        final Family.Member wally = family.get("WALLY");

        assertThat(BROTHER_IN_LAW.to(patty), is(wally));
    }

    private Matcher<? super Set<Family.Member>> is(Family.Member ... member) {
        return Matchers.hasItems(member);
    }
}