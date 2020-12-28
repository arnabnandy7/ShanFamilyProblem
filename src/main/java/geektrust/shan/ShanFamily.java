package geektrust.shan;

import geektrust.family.Family;
import geektrust.family.Family.Member;
import geektrust.family.Gender;

import static geektrust.family.relations.Relation.BROTHER;
import static geektrust.family.relations.Relation.SISTER;
import static geektrust.family.relations.Relation.SON;
import static geektrust.family.relations.Relation.DAUGHTER;
import static geektrust.family.relations.Relation.BROTHER_IN_LAW;
import static geektrust.family.relations.Relation.SISTER_IN_LAW;
import static geektrust.family.relations.Relation.PATERNAL_UNCLE;
import static geektrust.family.relations.Relation.MATERNAL_UNCLE;
import static geektrust.family.relations.Relation.PATERNAL_AUNT;
import static geektrust.family.relations.Relation.MATERNAL_AUNT;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Arnab Nandy
 */
public class ShanFamily {

	public static void main(String[] args) {
		Family shanFamily = new Family(ShanFamily::populate);

		String pathOfInputFile = args[0];

		File file = new File(pathOfInputFile);

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String st;
			while ((st = br.readLine()) != null) {
				String input = st;
				if (input.contains("ADD_CHILD")) {
					input = input.replace("ADD_CHILD ", "");
					List<String> inputArgs = Arrays.asList(input.split(" "));
					Family.Member child = shanFamily.addMember(inputArgs.get(1).toLowerCase(),
							inputArgs.get(2).equalsIgnoreCase("Female") ? Gender.FEMALE : Gender.MALE);
					try {
						Family.Member mother = shanFamily.get(inputArgs.get(0).toLowerCase());
						if (mother.getGender().toString().equalsIgnoreCase("Male")) {
							System.out.println("CHILD_ADDITION_FAILED");
						} else {
							Family.Member father = mother.getSpouse();
							mother.isParentOf(child);
							father.isParentOf(child);
							System.out.println("CHILD_ADDITION_SUCCEEDED");
						}
					} catch (Exception e) {
						System.out.println("PERSON_NOT_FOUND");
					}
				} else if (input.contains("GET_RELATIONSHIP")) {
					input = input.replace("GET_RELATIONSHIP ", "");
					List<String> inputArgs = Arrays.asList(input.split(" "));
					try {
						Family.Member member = shanFamily.get(inputArgs.get(0).toLowerCase());
						switch (inputArgs.get(1)) {
						case "Paternal-Uncle":
							Set<Member> mempu = new HashSet<>();
							if (PATERNAL_UNCLE.to(member).size() > 0) {
								mempu.addAll(PATERNAL_UNCLE.to(member));
								mempu.remove(shanFamily.get("U_N_K_N_O_W_N"));
								mempu.forEach(m -> System.out.print(initCap(m.getName()) + " "));
								System.out.println();
								mempu.clear();
							} else
								System.out.println("NONE");
							break;
						case "Maternal-Uncle":
							Set<Member> memmu = new HashSet<>();
							if (MATERNAL_UNCLE.to(member).size() > 0) {
								memmu.addAll(MATERNAL_UNCLE.to(member));
								memmu.remove(shanFamily.get("U_N_K_N_O_W_N"));
								memmu.forEach(m -> System.out.print(initCap(m.getName()) + " "));
								System.out.println();
								memmu.clear();
							} else
								System.out.println("NONE");
							break;
						case "Paternal-Aunt":
							Set<Member> mempa = new HashSet<>();
							if (PATERNAL_AUNT.to(member).size() > 0) {
								mempa.addAll(PATERNAL_AUNT.to(member));
								mempa.remove(shanFamily.get("U_N_K_N_O_W_N"));
								mempa.forEach(m -> System.out.print(initCap(m.getName()) + " "));
								System.out.println();
								mempa.clear();
							} else
								System.out.println("NONE");
							break;
						case "Maternal-Aunt":
							Set<Member> memma = new HashSet<>();
							if (MATERNAL_AUNT.to(member).size() > 0) {
								memma.addAll(MATERNAL_AUNT.to(member));
								memma.remove(shanFamily.get("U_N_K_N_O_W_N"));
								memma.forEach(m -> System.out.print(initCap(m.getName()) + " "));
								System.out.println();
								memma.clear();
							} else
								System.out.println("NONE");
							break;
						case "Sister-In-Law":
							Set<Member> child = member.getFather().getChildren();
							Set<Member> childHus = new HashSet<>();
							if (!member.getSpouse().getName().equals("U_N_K_N_O_W_N")) {
								childHus = member.getSpouse().getFather().getChildren();
							}
							child = child.stream().filter(c -> c.getGender().toString().equals("MALE"))
									.collect(Collectors.toSet());
							childHus = childHus.stream().filter(c -> c.getGender().toString().equals("FEMALE"))
									.collect(Collectors.toSet());
							if (child.isEmpty() && childHus.isEmpty())
								System.out.println("NONE");
							else if (child.isEmpty() && !childHus.isEmpty()) {
								childHus.forEach(m -> System.out.print(initCap(m.getName()) + " "));
								System.out.println();
							} else if (!child.isEmpty() && childHus.isEmpty()) {
								child.forEach(m -> System.out.print(initCap(m.getSpouse().getName()) + " "));
								System.out.println();
							} else {
								child.forEach(m -> System.out.print(initCap(m.getSpouse().getName()) + " "));
								childHus.forEach(m -> System.out.print(initCap(m.getName()) + " "));
								System.out.println();
							}
							child.clear();
							break;
						case "Brother-In-Law":
							Set<Member> membil = new HashSet<>();
							if (BROTHER_IN_LAW.to(member).size() > 0) {
								membil.addAll(BROTHER_IN_LAW.to(member));
								membil.remove(shanFamily.get("U_N_K_N_O_W_N"));
								membil.forEach(m -> System.out.print(initCap(m.getName()) + " "));
								System.out.println();
								membil.clear();
							} else
								System.out.println("NONE");
							break;
						case "Son":
							Set<Member> mems = new HashSet<>();
							if (SON.to(member).size() > 0) {
								mems.addAll(SON.to(member));
								mems.remove(shanFamily.get("U_N_K_N_O_W_N"));
								mems.forEach(m -> System.out.print(initCap(m.getName()) + " "));
								System.out.println();
								mems.clear();
							} else
								System.out.println("NONE");
							break;
						case "Daughter":
							Set<Member> memd = new HashSet<>();
							if (DAUGHTER.to(member).size() > 0) {
								memd.addAll(DAUGHTER.to(member));
								memd.remove(shanFamily.get("U_N_K_N_O_W_N"));
								memd.forEach(m -> System.out.print(initCap(m.getName()) + " "));
								System.out.println();
								memd.clear();
							} else
								System.out.println("NONE");
							break;
						case "Siblings":
							Set<Member> memsib = new HashSet<>();
							if (BROTHER.to(member).size() > 0 || SISTER.to(member).size() > 0) {
								if (BROTHER.to(member).size() == 0 && SISTER.to(member).size() > 0) {
									memsib.addAll(SISTER.to(member));
									memsib.remove(shanFamily.get("U_N_K_N_O_W_N"));
									memsib.forEach(m -> System.out.print(initCap(m.getName()) + " "));
									System.out.println();
								} else if (BROTHER.to(member).size() > 0 && SISTER.to(member).size() == 0) {
									memsib.addAll(BROTHER.to(member));
									memsib.remove(shanFamily.get("U_N_K_N_O_W_N"));
									memsib.forEach(m -> System.out.print(initCap(m.getName()) + " "));
									System.out.println();
								} else if (BROTHER.to(member).size() > 0 && SISTER.to(member).size() > 0) {
									memsib.addAll(BROTHER.to(member));
									memsib.addAll(SISTER.to(member));
									memsib.remove(shanFamily.get("U_N_K_N_O_W_N"));
									memsib.forEach(m -> System.out.print(initCap(m.getName()) + " "));
									System.out.println();
								}
								memsib.clear();
							} else
								System.out.println("NONE");
							break;
						default:
							break;
						}
					} catch (Exception e) {
						System.out.println("PERSON_NOT_FOUND");
					}
				}
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	private static String initCap(String name) {
		return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
	}

	private static void populate(Family family) {
		// Members
		Family.Member kingShan = family.addMember("king shan", Gender.MALE);
		Family.Member queenAnga = family.addMember("queen anga", Gender.FEMALE);

		Family.Member chit = family.addMember("chit", Gender.MALE);
		Family.Member amba = family.addMember("amba", Gender.FEMALE);

		Family.Member ish = family.addMember("ish", Gender.MALE);

		Family.Member vich = family.addMember("vich", Gender.MALE);
		Family.Member lika = family.addMember("lika", Gender.FEMALE);

		Family.Member vila = family.addMember("vila", Gender.FEMALE);
		Family.Member chika = family.addMember("chika", Gender.FEMALE);

		Family.Member aras = family.addMember("aras", Gender.MALE);
		Family.Member chitra = family.addMember("chitra", Gender.FEMALE);

		Family.Member arit = family.addMember("arit", Gender.MALE);
		Family.Member jnki = family.addMember("jnki", Gender.FEMALE);

		Family.Member laki = family.addMember("laki", Gender.MALE);
		Family.Member lavnya = family.addMember("lavnya", Gender.FEMALE);

		Family.Member ahit = family.addMember("ahit", Gender.MALE);

		Family.Member satya = family.addMember("satya", Gender.FEMALE);
		Family.Member vyan = family.addMember("vyan", Gender.MALE);

		Family.Member satvy = family.addMember("satvy", Gender.FEMALE);
		Family.Member asva = family.addMember("asva", Gender.MALE);

		Family.Member vasa = family.addMember("vasa", Gender.MALE);

		Family.Member krpi = family.addMember("krpi", Gender.FEMALE);
		Family.Member vyas = family.addMember("vyas", Gender.MALE);

		Family.Member kriya = family.addMember("kriya", Gender.MALE);
		Family.Member krithi = family.addMember("krithi", Gender.FEMALE);

		Family.Member atya = family.addMember("atya", Gender.FEMALE);

		Family.Member dritha = family.addMember("dritha", Gender.FEMALE);
		Family.Member jaya = family.addMember("jaya", Gender.MALE);

		Family.Member yodhan = family.addMember("yodhan", Gender.MALE);

		Family.Member tritha = family.addMember("tritha", Gender.FEMALE);
		Family.Member vritha = family.addMember("vritha", Gender.MALE);

		// Relationships
		kingShan.isSpouseOf(queenAnga);
		kingShan.isParentOf(chit);
		kingShan.isParentOf(ish);
		kingShan.isParentOf(vich);
		kingShan.isParentOf(aras);
		kingShan.isParentOf(satya);
		queenAnga.isParentOf(chit);
		queenAnga.isParentOf(ish);
		queenAnga.isParentOf(vich);
		queenAnga.isParentOf(aras);
		queenAnga.isParentOf(satya);

		chit.isSpouseOf(amba);
		chit.isParentOf(dritha);
		chit.isParentOf(tritha);
		chit.isParentOf(vritha);
		amba.isParentOf(dritha);
		amba.isParentOf(tritha);
		amba.isParentOf(vritha);

		dritha.isSpouseOf(jaya);
		dritha.isParentOf(yodhan);
		jaya.isParentOf(yodhan);

		vich.isSpouseOf(lika);
		vich.isParentOf(vila);
		vich.isParentOf(chika);
		lika.isParentOf(vila);
		lika.isParentOf(chika);

		aras.isSpouseOf(chitra);
		aras.isParentOf(jnki);
		aras.isParentOf(ahit);
		chitra.isParentOf(jnki);
		chitra.isParentOf(ahit);

		arit.isSpouseOf(jnki);
		arit.isParentOf(laki);
		arit.isParentOf(lavnya);
		jnki.isParentOf(laki);
		jnki.isParentOf(lavnya);

		vyan.isSpouseOf(satya);
		vyan.isParentOf(asva);
		vyan.isParentOf(vyas);
		vyan.isParentOf(atya);
		satya.isParentOf(asva);
		satya.isParentOf(vyas);
		satya.isParentOf(atya);

		asva.isSpouseOf(satvy);
		asva.isParentOf(vasa);
		satvy.isParentOf(vasa);

		vyas.isSpouseOf(krpi);
		vyas.isParentOf(kriya);
		vyas.isParentOf(krithi);
		krpi.isParentOf(kriya);
		krpi.isParentOf(krithi);
	}
}
