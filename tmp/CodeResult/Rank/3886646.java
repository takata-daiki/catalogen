package org.gbif.ecat.voc;

import org.gbif.dwc.terms.DwcTerm;
import org.gbif.ecat.utils.StringLengthComparator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

public enum Rank implements KnownTerm {
  Domain(0, "dom."), KINGDOM(25, "king."), Subkingdom(50, "subking."), Superphylum(60, "superphyl."), PHYLUM(75,
    "phyl."), Subphylum(100, "subphyl."), Superclass(125, "supercl."), CLASS(150, "cl."), Subclass(175,
    "subcl."), Superorder(200, "superord."), ORDER(225, "ord."), Suborder(250, "subord."), Infraorder(275,
    null), Superfamily(300, "superfam."), FAMILY(325, "fam."), Subfamily(350, "subfam."), Tribe(375, "trib."), Subtribe(
    400, "subtrib."), SupragenericName(410, "supergen."), // used for any other unspecific rank above genera
  GENUS(425, "gen."), SUBGENUS(450, "subgen."), Section(475, "sect."), Subsection(500, "subsect."), Series(525,
    "ser."), Subseries(550, "subser."), InfragenericName(575,
    "infragen."), // used for any other unspecific rank below genera and above
  // species
  SPECIES(600, "sp."), InfraspecificName(625, "agg."), // used for any other unspecific rank below species
  SUBSPECIES(650, "subsp."), InfrasubspecificName(675, "infrasubsp."), // used also for any other unspecific rank below
  // subspecies
  VARIETY(700, "var."), Subvariety(725, "subvar."), Form(750, "f."), Subform(775, "subf."), CultivarGroup(800,
    null), Cultivar(825, "cv."), Strain(835, "strain"), Informal(850, null), Unranked(875, null);

  private final int id; // reserved upto 999
  public final String marker; // reserved upto 999
  private static final int ID_BASE = 0; // reserved upto 999
  public static final Rank[] KINGDOM_TO_SUBGENUS =
    new Rank[] {Rank.KINGDOM, Rank.PHYLUM, Rank.CLASS, Rank.ORDER, Rank.FAMILY, Rank.GENUS, Rank.SUBGENUS};
  public static final Rank[] LINNEAN_RANKS =
    new Rank[] {Rank.KINGDOM, Rank.PHYLUM, Rank.CLASS, Rank.ORDER, Rank.FAMILY, Rank.GENUS, Rank.SPECIES};
  public static final Set<Rank> MAYOR_RANKS;

  static {
    Set<Rank> mayorRanks = new HashSet<Rank>();
    mayorRanks.add(KINGDOM);
    mayorRanks.add(PHYLUM);
    mayorRanks.add(CLASS);
    mayorRanks.add(ORDER);
    mayorRanks.add(FAMILY);
    mayorRanks.add(GENUS);
    mayorRanks.add(SUBGENUS);
    mayorRanks.add(SPECIES);
    MAYOR_RANKS = ImmutableSet.copyOf(mayorRanks);
  }

  public static final Set<Rank> UNCOMPARABLE_RANKS;

  static {
    Set<Rank> ranks = new HashSet<Rank>();
    ranks.add(InfragenericName);
    ranks.add(InfraspecificName);
    ranks.add(InfrasubspecificName);
    ranks.add(Informal);
    ranks.add(Unranked);
    // ranks.add(SPECIES);
    UNCOMPARABLE_RANKS = ImmutableSet.copyOf(ranks);
  }

  public static final Set<Rank> SPECIES_OR_BELOW;

  static {
    Set<Rank> ranks = new HashSet<Rank>();
    ranks.add(SPECIES);
    ranks.add(InfraspecificName);
    ranks.add(SUBSPECIES);
    ranks.add(InfrasubspecificName);
    ranks.add(VARIETY);
    ranks.add(Subvariety);
    ranks.add(Form);
    ranks.add(Subform);
    ranks.add(CultivarGroup);
    ranks.add(Cultivar);
    ranks.add(Strain);
    SPECIES_OR_BELOW = ranks;
  }

  public static final Set<Rank> SUPRAGENERIC;

  static {
    Set<Rank> ranks = new HashSet<Rank>();
    ranks.add(Domain);
    ranks.add(KINGDOM);
    ranks.add(Subkingdom);
    ranks.add(Superphylum);
    ranks.add(PHYLUM);
    ranks.add(Subphylum);
    ranks.add(Superclass);
    ranks.add(CLASS);
    ranks.add(Subclass);
    ranks.add(Superorder);
    ranks.add(ORDER);
    ranks.add(Suborder);
    ranks.add(Infraorder);
    ranks.add(Superfamily);
    ranks.add(FAMILY);
    ranks.add(Subfamily);
    ranks.add(Tribe);
    ranks.add(Subtribe);
    ranks.add(SupragenericName);
    SUPRAGENERIC = ImmutableSet.copyOf(ranks);
  }

  public static final Map<String, Rank> RANK_MARKER_MAP_SUPRAGENERIC;

  static {
    HashMap<String, Rank> ranks = new HashMap<String, Rank>();
    ranks.put("fam", Rank.FAMILY);
    ranks.put("trib", Rank.Tribe);
    ranks.put("sect", Rank.Section);
    ranks.put("supertrib", Rank.SupragenericName);
    ranks.put("supersubtrib", Rank.SupragenericName);
    ranks.put("ib", Rank.SupragenericName);
    ranks.put("gen", Rank.GENUS);
    RANK_MARKER_MAP_SUPRAGENERIC = ImmutableMap.copyOf(ranks);
  }

  public static final Map<String, Rank> RANK_MARKER_MAP_INFRAGENERIC;

  static {
    HashMap<String, Rank> ranks = new HashMap<String, Rank>();
    ranks.put("subgenus", Rank.SUBGENUS);
    ranks.put("subgen", Rank.SUBGENUS);
    ranks.put("subg", Rank.SUBGENUS);
    ranks.put("section", Rank.Section);
    ranks.put("sect", Rank.Section);
    ranks.put("subsection", Rank.Subsection);
    ranks.put("subsect", Rank.Subsection);
    ranks.put("series", Rank.Series);
    ranks.put("ser", Rank.Series);
    ranks.put("subseries", Rank.Subseries);
    ranks.put("subser", Rank.Subseries);
    ranks.put("agg", Rank.InfragenericName);
    ranks.put("species", Rank.SPECIES);
    ranks.put("spec", Rank.SPECIES);
    ranks.put("spp", Rank.SPECIES);
    ranks.put("sp", Rank.SPECIES);
    RANK_MARKER_MAP_INFRAGENERIC = ImmutableMap.copyOf(ranks);
  }

  public static final Map<String, Rank> RANK_MARKER_MAP_INFRASPECIFIC;

  static {
    HashMap<String, Rank> ranks = new HashMap<String, Rank>();
    ranks.put("subsp", Rank.SUBSPECIES);
    ranks.put("ssp", Rank.SUBSPECIES);
    ranks.put("var", Rank.VARIETY);
    ranks.put("v", Rank.VARIETY);
    ranks.put("subvar", Rank.Subvariety);
    ranks.put("subv", Rank.Subvariety);
    ranks.put("sv", Rank.Subvariety);
    ranks.put("forma", Rank.Form);
    ranks.put("form", Rank.Form);
    ranks.put("fo", Rank.Form);
    ranks.put("f", Rank.Form);
    ranks.put("subform", Rank.Subform);
    ranks.put("subf", Rank.Subform);
    ranks.put("sf", Rank.Subform);
    ranks.put("cv", Rank.Cultivar);
    ranks.put("hort", Rank.Cultivar);
    ranks.put("m", Rank.InfrasubspecificName);
    ranks.put("morph", Rank.InfrasubspecificName);
    ranks.put("nat", Rank.InfrasubspecificName);
    ranks.put("ab", Rank.InfrasubspecificName);
    ranks.put("aberration", Rank.InfrasubspecificName);
    ranks.put("\\*+", Rank.InfraspecificName);
    RANK_MARKER_MAP_INFRASPECIFIC = ImmutableMap.copyOf(ranks);
  }

  public static final Map<String, Rank> RANK_MARKER_MAP;

  static {
    HashMap<String, Rank> ranks = new HashMap<String, Rank>();
    ranks.putAll(RANK_MARKER_MAP_SUPRAGENERIC);
    ranks.putAll(RANK_MARKER_MAP_INFRAGENERIC);
    ranks.putAll(RANK_MARKER_MAP_INFRASPECIFIC);
    ranks.put("subser", Rank.Subseries);
    RANK_MARKER_MAP = ImmutableMap.copyOf(ranks);
  }

  /**
   * A map of name suffices to corresponding ranks across all kingdoms.
   * To minimize wrong matches this map is sorted by suffix length with the first suffices being the longest and
   * therefore most accurate matches
   */
  public static final SortedMap<String, Rank> SUFFICES_RANK_MAP;

  static {
    SortedMap<String, Rank> ranks = new TreeMap<String, Rank>(new StringLengthComparator());
    ranks.put("mycetidae", Rank.Subclass);
    ranks.put("phycidae", Rank.Subclass);
    ranks.put("mycotina", Rank.Subphylum);
    ranks.put("phytina", Rank.Subphylum);
    ranks.put("phyceae", Rank.CLASS);
    ranks.put("mycetes", Rank.CLASS);
    ranks.put("mycota", Rank.PHYLUM);
    ranks.put("opsida", Rank.CLASS);
    ranks.put("oideae", Rank.Subfamily);
    ranks.put("aceae", Rank.FAMILY);
    ranks.put("phyta", Rank.PHYLUM);
    ranks.put("oidea", Rank.Superfamily);
    ranks.put("anae", Rank.Superorder);
    ranks.put("ales", Rank.ORDER);
    ranks.put("acea", Rank.Superfamily);
    ranks.put("idae", Rank.FAMILY);    // zoology only, in botany a subclass !!!
    ranks.put("inae", Rank.Subfamily); // zoology only, in botany a subtribe !!!
    ranks.put("eae", Rank.Tribe);
    ranks.put("ini", Rank.Tribe);
    SUFFICES_RANK_MAP = ranks;
  }

  private Rank(int id, String marker) {
    this.id = id;
    this.marker = marker;
  }

  /**
   * @param rankAbbreviation k,sk,p,sp,c,sc,o,so,f,sf,g,sg,s,ss,v,ff for kingdom, subkingdom, phylum, ..., forma
   *
   * @return the matching rank enum or null
   */
  public static Rank fromAbbreviation(String rankAbbreviation) {
    if (rankAbbreviation.equalsIgnoreCase("k")) {
      return KINGDOM;
    }
    if (rankAbbreviation.equalsIgnoreCase("sk")) {
      return Subkingdom;
    }
    if (rankAbbreviation.equalsIgnoreCase("p")) {
      return PHYLUM;
    }
    if (rankAbbreviation.equalsIgnoreCase("sp")) {
      return Subphylum;
    }
    if (rankAbbreviation.equalsIgnoreCase("c")) {
      return CLASS;
    }
    if (rankAbbreviation.equalsIgnoreCase("sc")) {
      return Subclass;
    }
    if (rankAbbreviation.equalsIgnoreCase("o")) {
      return ORDER;
    }
    if (rankAbbreviation.equalsIgnoreCase("so")) {
      return Suborder;
    }
    if (rankAbbreviation.equalsIgnoreCase("f")) {
      return FAMILY;
    }
    if (rankAbbreviation.equalsIgnoreCase("sf")) {
      return Subfamily;
    }
    if (rankAbbreviation.equalsIgnoreCase("g")) {
      return GENUS;
    }
    if (rankAbbreviation.equalsIgnoreCase("sg")) {
      return SUBGENUS;
    }
    if (rankAbbreviation.equalsIgnoreCase("s")) {
      return SPECIES;
    }
    if (rankAbbreviation.equalsIgnoreCase("ss")) {
      return SUBSPECIES;
    }
    if (rankAbbreviation.equalsIgnoreCase("v")) {
      return VARIETY;
    }
    if (rankAbbreviation.equalsIgnoreCase("ff")) {
      return Form;
    }
    return null;
  }

  public static Rank fromDwcTerm(DwcTerm higherRankTerm) {
    if (higherRankTerm == DwcTerm.kingdom) {
      return Rank.KINGDOM;
    } else if (higherRankTerm == DwcTerm.phylum) {
      return Rank.PHYLUM;
    } else if (higherRankTerm == DwcTerm.classs) {
      return Rank.CLASS;
    } else if (higherRankTerm == DwcTerm.order) {
      return Rank.ORDER;
    } else if (higherRankTerm == DwcTerm.family) {
      return Rank.FAMILY;
    } else if (higherRankTerm == DwcTerm.genus) {
      return Rank.GENUS;
    } else if (higherRankTerm == DwcTerm.subgenus) {
      return Rank.SUBGENUS;
    }
    return null;
  }

  /**
   * Infers, ie, guesses, a rank based on parsed name atoms.
   * As a final resort for higher monomials the suffices are inspected, but no attempt is made to disambiguate
   * the 2 known homonym suffices -idae and -inae, but instead the far more widespread zoological versions are
   * interpreted.
   * TODO: pass optional nomenclatural code paraemeter to disambiguate homonym suffices.
   *
   * @return never null, but possibly Unranked
   */
  public static Rank inferRank(String genusOrAbove, String infraGeneric, String specificEpithet, String rankMarker,
    String infraSpecificEpithet) {
    // first try rank marker
    Rank markerRank = inferRankMarker(rankMarker);
    if (markerRank != null) {
      return markerRank;
    }

    // default if we cant find anything else
    Rank rank = Rank.Unranked;
    // detect rank based on parsed name
    if (infraSpecificEpithet != null) {
      // some infraspecific name
      rank = Rank.InfraspecificName;
    } else if (specificEpithet != null) {
      // a species
      rank = Rank.SPECIES;
    } else if (infraGeneric != null) {
      // some infrageneric name
      rank = Rank.InfragenericName;
    } else if (genusOrAbove != null) {
      // a suprageneric name, check suffices
      for (String suffix : Rank.SUFFICES_RANK_MAP.keySet()) {
        if (genusOrAbove.endsWith(suffix)) {
          rank = Rank.SUFFICES_RANK_MAP.get(suffix);
          break;
        }
      }
    }
    return rank;
  }

  public static Rank inferRankMarker(String rankMarker) {
    // first try rank marker
    if (rankMarker != null) {
      Rank markerRank = RANK_MARKER_MAP.get(rankMarker.toLowerCase().replaceAll("[._-]", ""));
      if (markerRank != null) {
        return markerRank;
      }
    }
    return null;
  }

  public static String toAbbreviation(Rank mayorRank) {
    if (mayorRank == Rank.KINGDOM) {
      return ("k");
    } else if (mayorRank == Rank.Subkingdom) {
      return ("sk");
    } else if (mayorRank == Rank.PHYLUM) {
      return ("p");
    } else if (mayorRank == Rank.Subphylum) {
      return ("sp");
    } else if (mayorRank == Rank.CLASS) {
      return ("c");
    } else if (mayorRank == Rank.Subclass) {
      return ("sc");
    } else if (mayorRank == Rank.ORDER) {
      return ("o");
    } else if (mayorRank == Rank.Suborder) {
      return ("so");
    } else if (mayorRank == Rank.FAMILY) {
      return ("f");
    } else if (mayorRank == Rank.Subfamily) {
      return ("sf");
    } else if (mayorRank == Rank.GENUS) {
      return ("g");
    } else if (mayorRank == Rank.SUBGENUS) {
      return ("sg");
    } else if (mayorRank == Rank.SPECIES) {
      return ("s");
    } else if (mayorRank == Rank.InfraspecificName) {
      return ("is");
    } else if (mayorRank == Rank.VARIETY) {
      return ("v");
    } else if (mayorRank == Rank.Form) {
      return ("ff");
    }
    return null;
  }

  public static DwcTerm toDwcTerm(Rank rank) {
    if (rank == Rank.KINGDOM) {
      return DwcTerm.kingdom;
    } else if (rank == Rank.PHYLUM) {
      return DwcTerm.phylum;
    } else if (rank == Rank.CLASS) {
      return DwcTerm.classs;
    } else if (rank == Rank.ORDER) {
      return DwcTerm.order;
    } else if (rank == Rank.FAMILY) {
      return DwcTerm.family;
    } else if (rank == Rank.GENUS) {
      return DwcTerm.genus;
    } else if (rank == Rank.SUBGENUS) {
      return DwcTerm.subgenus;
    }
    return null;
  }

  public static Rank valueOfTermID(Integer termID) {
    for (Rank term : Rank.values()) {
      if (termID != null && term.termID() == termID) {
        return term;
      }
    }
    return null;
  }

  public boolean isSpeciesOrBelow() {
    return SPECIES_OR_BELOW.contains(this);
  }

  /**
   * @return true for infraspecific ranks excluding cultivars and strains.
   */
  public boolean isInfraspecific() {
    if (SPECIES_OR_BELOW.contains(this)) {
      if (this != SPECIES && this != CultivarGroup && this != Cultivar && this != Strain) {
        return true;
      }
    }
    return false;
  }

  public boolean isSuprageneric() {
    return SUPRAGENERIC.contains(this);
  }

  /**
   * True for all mayor Linn?an ranks, ie kingdom,phylum,class,order,family,genus and species
   */
  public boolean isLinnean() {
    for (Rank r : LINNEAN_RANKS) {
      if (r == this) {
        return true;
      }
    }
    return false;
  }

  /**
   * True for names of informal ranks that represent a range of ranks really and therefore cannot safely be compared to
   * other ranks in all cases.
   * Example ranks are infraspecies or subgeneric
   *
   * @return true if uncomparable
   */
  public boolean isUncomparable() {
    return UNCOMPARABLE_RANKS.contains(this);
  }

  @Override
  public int termID() {
    return ID_BASE + this.id;
  }

  @Override
  public TermType type() {
    return TermType.RANK;
  }

}
