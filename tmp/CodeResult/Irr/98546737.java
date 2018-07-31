/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ClassModules;
import Modules.AIGameData;
import Modules.BuildingIdx;
import Modules.Constants;
import static Modules.Constants.*;
import Modules.ImageIdx;
import Modules.MsgBox;
import Modules.ResourceIdx;
import static Modules.PRGameData.*;
import static Modules.ResourceIdx.*;
import Modules.PRShipFirstHoldIdx;
import Modules.PRShipLastHoldIdx;
import Modules.PRShips;
import Modules.RevShipIdx;
import Modules.SetupAvailableRoles;
import Modules.SetupPlayerNames;
import Modules.ShipIdx;
import Modules.ShipTypes;
import Modules.ShippingPick;
import Modules.ShipsLeft;
import static Modules.Text1Idx.*;
import Modules.Utilities;
import java.util.EnumSet;
import javax.swing.JOptionPane;

/**
 *
 * @author steve
 */
public class CargoShip {
private static PRShips ShipsInPlay[] = new PRShips [PRCNumShips];
private static Modules.ResourceIdx GoodPickedToShip = Modules.ResourceIdx.NullRIdx;
private static boolean WhoFinishedShipping[] = new boolean[Constants.MinPlayers];
private static boolean CaptainsPrivilegeTaken = false;
private static boolean WharfUsed[] = new boolean[Constants.MinPlayers];

public static void FreeHolds(ShipIdx sWhichShip ){
 int WhichShip = sWhichShip.ordinal();
 for (int i = ShipsInPlay[WhichShip].FirstHold.gi(); i <= ShipsInPlay[WhichShip]
	 .LastHold.gi(); i++){
  //take items from ship
  PRPuertoRico.playermat.Image6[i].setName( NullRIdx.toString());
  PRPuertoRico.playermat.Image6[i].setVisible(false);
 };

 //add items to resource bank
 PRPuertoRico.playermat.Text1[ShipsInPlay[WhichShip].Good.ordinal() +
	 IndigoBankIdx.ordinal()].setText(Integer.toString(
   Integer.valueOf(PRPuertoRico.playermat.Text1[ShipsInPlay[WhichShip].Good.ordinal() +
   IndigoBankIdx.ordinal()].getText()) + ShipsInPlay[WhichShip].NumHoldsFull));
 //clean up ship database
 ShipsInPlay[WhichShip].Good = NullRIdx;
 ShipsInPlay[WhichShip].NumHoldsFull = 0;

};

public static boolean IsShipEmpty (ShipIdx sWhichShip) {
 // Can't handle null ship or wharf here
 if (sWhichShip.compareTo(ShipIdx.LargeShip) > 0)
  return false;
 
 return ShipsInPlay[sWhichShip.ordinal()].NumHoldsFull == 0;
}

public static boolean EmptyShip(ShipIdx sWhichShip ){
 int WhichShip = sWhichShip.ordinal();

 if ( sWhichShip != ShipIdx.WharfSlot )
  return (ShipsInPlay[WhichShip].Good == NullRIdx);
 else
  return false;
 };

public static ShipIdx ShipForThisGood(Modules.ResourceIdx BarrelType ){
int NumCouldShip = 0;
ShipIdx ShipForThisGood = ShipIdx.NotAShipSlot;

 if ( BarrelType.ordinal() == NullRIdx.ordinal() )
   return ShipForThisGood;

 NumCouldShip = Integer.valueOf(PRFArray[PRGDCurrentPlayer.ordinal()].
	  Text1[IndigoIdx.ordinal() + BarrelType.ordinal()].getText());
 if ( NumCouldShip == 0 ) {
  return ShipForThisGood;
 };

  //check for wharf
 if ( CheckForWharf () ) 
   ShipForThisGood = ShipIdx.WharfSlot;
//   GoodPickedToShip = BarrelType;

  //Check for Partial Ship
  for ( ShipIdx si: EnumSet.range(ShipIdx.SmallShip ,ShipIdx.LargeShip)){
   int i = si.ordinal();
   if ( ShipsInPlay[i].Good.ordinal() == BarrelType.ordinal() ) {
    if ( ShipsInPlay[i].NumHoldsFull < ShipsInPlay[i].Capacity ) {
     ShipForThisGood = si;
//     GoodPickedToShip = BarrelType;
    };
    return ShipForThisGood;
   };
  }; 

  //Check for Empty Ships
  for (ShipIdx si : EnumSet.range( ShipIdx.SmallShip , ShipIdx.LargeShip)){
   int i = si.ordinal();
   if ( (ShipsInPlay[i].Good.ordinal() == NullRIdx.ordinal()) ) {
    ShipForThisGood = si;
//    GoodPickedToShip = BarrelType;
//    if ( NumCouldShip < ShipsInPlay[i].Capacity )
     return ShipForThisGood;
   };
  }; 

 return ShipForThisGood;
};

public static void SetCandidateBarrel(ResourceIdx Barrel) {
 GoodPickedToShip = Barrel;
}

public static boolean CheckForWharf () {
  if ( Building.Production(PRGDCurrentPlayer, BuildingIdx.WharfIdx) > 0 )
   if ( WharfUsed[PRGDCurrentPlayer.ordinal()] ) {}
   else
    return true;

   return false;
}

public static boolean ShipWithWharf(SetupPlayerNames sPlayer) { 
// Call this after 'done' clicked upon being asked to pick a ship
int Player = sPlayer.ordinal();	

// check for wharf
// If I can't ship without a wharf
//  is wharf to be used
//   no: next shipper (return false)
//   yes: use wharf
// Ship using wharf
//
  if ( CheckForWharf() ) {
   
   if (ShipWithoutWharf(sPlayer))
    return true;
   else
     if ( PRGDAutoPlayer[Player] )
      return AutoPlayer.DealWithWharf(sPlayer);
     else
      if ( MsgBox.Inform (PRGDPlayerNameStrings[Player] +
	" Do You Want to Use Your Wharf?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) 
//      PRFArray[Player].Command1.setVisible(true);
       return true;
      else {
//      PRFArray[Player].Command1.setVisible(false);
       PRPuertoRico.playermat.Picture1[ShipTypes.PlayerWharf.ordinal()].setVisible(false);
       return false;
      }

//   PRPuertoRico.playermat.Picture1[ShipTypes.PlayerWharf.ordinal()].setVisible(true);
  }
 return false;
}

public static boolean ShipWithoutWharf (SetupPlayerNames sPlayer) {
int Player = sPlayer.ordinal();
Modules.ResourceIdx OnGood;
boolean CheckedForEmpty = false;
boolean GoodAlreadyInShip = false;
	
// Loop over goods and check for a good and a ship
for (Modules.Text1Idx tiR : EnumSet.range( IndigoIdx , CoffeeIdx)){
 int iR = tiR.ordinal();
 GoodAlreadyInShip = false;
 OnGood = Modules.ResourceIdx.values()[iR - IndigoIdx.ordinal()]; //TODO:: reverse ENUM

 if (Integer.valueOf(PRFArray[Player].Text1[tiR.ordinal()].getText()) == 0)
  continue;

 //Check for Partial Ship
 for ( ShipIdx si: EnumSet.range(ShipIdx.SmallShip ,ShipIdx.LargeShip)){
  int i = si.ordinal();
  if ( ShipsInPlay[i].Good == OnGood ) {
   if ( ShipsInPlay[i].NumHoldsFull < ShipsInPlay[i].Capacity )
    return true;
   GoodAlreadyInShip = true;
   break; // Can't ship this good, on to the next!
  };
 };

  //Check for Empty Ships
 if (CheckedForEmpty) {} // Already checked
 else
  if (GoodAlreadyInShip) {} // Good already in a ship, try next good
  else	{
   for (ShipIdx si : EnumSet.range( ShipIdx.SmallShip , ShipIdx.LargeShip)){
    int i = si.ordinal();
    if ( (ShipsInPlay[i].Good == NullRIdx) )
     return true;
   };
   CheckedForEmpty = true;
   };

 };//for all shipable goods
 return false;
}

public static boolean CanIShip(SetupPlayerNames sPlayer,
                         SetupAvailableRoles CurrentRole ){ 
	
int Player = sPlayer.ordinal();
int BarrelTypes = Barrel.LeftOverTypesBarrels(sPlayer);

if (BarrelTypes == 0) {}
else
 if (ShipWithWharf(sPlayer))
  return true;
 else
  if (ShipWithoutWharf(sPlayer))
   return true;

 // The only way to get here is if you've no shipping to do
 if ( CurrentRole == SetupAvailableRoles.CaptainSAR )
  WhoFinishedShipping[Player] = true;
 return false;
};

public static boolean EveryoneShipped(){
// In Captain phase, call this to see if everyone has shipped all they can
// WhoFinishedShipping must all be true to return true!
 for (int iP = SetupPlayerNames.HumanPlayerSPN.ordinal(); iP <
	 PRGDActualNumPlayers; iP++){
  if ( WhoFinishedShipping[iP] ) {}
  else // Somebody still needs to ship
   return false;
};
 return true;
};

public static boolean AllGoodsStoredAfterShipping() {
// In GoodsNotShipped pseudo-phase, call this to see if everyone has cleared the necessary
// WhoFinishedShipping must all be false to return true!	
 for (int iP = SetupPlayerNames.HumanPlayerSPN.ordinal(); iP <
	 PRGDActualNumPlayers; iP++){
  if ( WhoFinishedShipping[iP] )
  // Somebody still needs to clear docks 
   return false;
};
 return true;	
}

public static void GoodsHoused(SetupPlayerNames sPlayer){
 int Player = sPlayer.ordinal();
 WhoFinishedShipping[Player] = false;
};

public static boolean LoadCargo(ShipIdx sWhichShip ){
int WhichShip = sWhichShip.ordinal();
boolean LoadCargo = false;
int NumCanShip;
int NumHoldsleft;
int NumVP;
String ShipName;

 if ( (GoodPickedToShip == NullRIdx) |
 (WhichShip == ShipIdx.NotAShipSlot.ordinal()) ) 
  return false;
 
 //how many resources could you ship
 NumCanShip = Integer.valueOf(PRFArray[PRGDCurrentPlayer.ordinal()].
	 Text1[GoodPickedToShip.ordinal() + IndigoIdx.ordinal()].getText());

 if (sWhichShip == ShipIdx.WharfSlot) {
  PRPuertoRico.playermat.Picture1[ShipTypes.PlayerWharf.ordinal()].setVisible(false);
  if ( CheckForWharf() ) {
   LoadCargo = true;
   WharfUsed[PRGDCurrentPlayer.ordinal()] = true;
   //add items to resource bank
   PRPuertoRico.playermat.Text1[GoodPickedToShip.ordinal() +
   IndigoBankIdx.ordinal()].setText(Integer.toString(
   Integer.valueOf(PRPuertoRico.playermat.Text1[GoodPickedToShip.ordinal() +
   IndigoBankIdx.ordinal()].getText())+ NumCanShip));
  };
 }
 else { // Not using Wharf

  //Check other ships haven't already got selected type of good
  for (ShipIdx spi : EnumSet.range( ShipIdx.SmallShip , ShipIdx.LargeShip)){
    // First make sure ship picked has either same cargo or is empty
    if (spi != sWhichShip)
     if ( (ShipsInPlay[spi.ordinal()].Good.compareTo(GoodPickedToShip) == 0))
//       (ShipsInPlay[WhichShip].Good.compareTo(NullRIdx) != 0 ))
      return false;
  }; 

  // Picked partial ship with different good?
  if ((ShipsInPlay[WhichShip].Good.compareTo(GoodPickedToShip) != 0) &
       (ShipsInPlay[WhichShip].Good.compareTo(NullRIdx) != 0 ))
	  return false;

  //how many holds are available
  NumHoldsleft = ShipsInPlay[WhichShip].Capacity - ShipsInPlay[WhichShip].NumHoldsFull;

  if ( NumHoldsleft > 0 ) {
//   if ( (ShipsInPlay[WhichShip].Good.ordinal() == GoodPickedToShip.ordinal()) |
//      (ShipsInPlay[WhichShip].Good.ordinal() == NullRIdx.ordinal()) ) {

    LoadCargo = true;
    if ( NumCanShip > NumHoldsleft ) 
     NumCanShip = NumHoldsleft;
    
    //add items to ship
    for (int i = ShipsInPlay[WhichShip].FirstHold.gi() + ShipsInPlay[WhichShip].NumHoldsFull;
     i < (ShipsInPlay[WhichShip].FirstHold.gi() + ShipsInPlay[WhichShip].NumHoldsFull
     + NumCanShip); i++) {
     ImageIdx GoodLetter = ImageIdx.values()[ImageIdx.IndigoLetter.ordinal() + GoodPickedToShip.ordinal()];
     javax.swing.JLabel jl =  PRPuertoRico.playermat.Image6[i];
     jl.setName(Integer.toString(GoodPickedToShip.ordinal()));

     jl.setIcon(PRFetchResource.FR(GoodLetter.SImgIndex()));

     jl.setVisible(true);
    }; 

    ShipsInPlay[WhichShip].NumHoldsFull = ShipsInPlay[WhichShip].NumHoldsFull + NumCanShip;
    ShipsInPlay[WhichShip].Good = GoodPickedToShip;
//   };
  };
 };

 if ( LoadCargo ) {
  if ( sWhichShip == ShipIdx.WharfSlot )
   ShipName = "Wharf";
  else
   ShipName = ShipsInPlay[WhichShip].Name + " Ship";
  
  Utilities.EventAnnounce( PRGDPlayerNameStrings[
	  PRGDCurrentPlayer.ordinal()] + " Ships " + Integer.toString(NumCanShip) + " " +

              PRGDResourceNStr[GoodPickedToShip.ordinal()] + " on the " + ShipName);
  //take items from playermat
  PRFArray[PRGDCurrentPlayer.ordinal()].Text1[GoodPickedToShip
	  .ordinal() + IndigoIdx.ordinal()].setText(Integer.toString(Integer.valueOf(
     PRFArray[PRGDCurrentPlayer.ordinal()].Text1[GoodPickedToShip
     .ordinal() + IndigoIdx.ordinal()].getText()) - NumCanShip));
  //remove vp from stock
  NumVP = NumCanShip;
  if ( CaptainsPrivilegeTaken ) {}
  else
   if ( PRGDCurrentPlayer == PRGDPrivilegedPlayer ) { //Captain//s Privilege
    NumVP = NumVP + 1;
    CaptainsPrivilegeTaken = true;
   };
  
  GoodPickedToShip = NullRIdx;

  if ( Building.Production(PRGDCurrentPlayer,
	  BuildingIdx.HarbourIdx) > 0 ) {
   NumVP = NumVP + 1;
//   result = Inform x = new ("Harbour Grants " + PRGDPlayerNameStrings(PRGDCurrentPlayer) + " +1 VP")
   Utilities.BldgAnnounce (BuildingIdx.HarbourIdx, "Harbour Grants " + 
	   PRGDPlayerNameStrings[PRGDCurrentPlayer.ordinal()] + " +1 VP");
  };

  //add vp to playermat
  VPChit.Garner (NumVP);

  PRFArray[PRGDCurrentPlayer.ordinal()].Command1.setVisible(false);
 };
 return LoadCargo;
};

public static void FlushBilge() {
 CaptainsPrivilegeTaken = false;
 for (ShipIdx si : EnumSet.range( ShipIdx.SmallShip , ShipIdx.LargeShip )){//Pick which ships to use
  int i = si.ordinal()	 ;
  if ( ShipsInPlay[i].NumHoldsFull == ShipsInPlay[i].Capacity ) {
   FreeHolds (si);
  };
 }; 
 for (int iP = SetupPlayerNames.HumanPlayerSPN.ordinal() ;
      iP < PRGDActualNumPlayers; iP++){
  WharfUsed[iP] = false;
 }; 
};

public static ShippingPick MaxHolds(SetupPlayerNames sPlayer) {
//AI uses this to determine what shipment to make
int Player = sPlayer.ordinal();
ShippingPick sp = new ShippingPick();
int CurrentMax =0;
int HowManyKinds = 0;
int BestHolds[] = new int [PRCNumGoods];
int NextBestHolds = 0;
ShipIdx BestShip[] = new ShipIdx [PRCNumGoods];
ShipIdx NextBestShip = ShipIdx.NotAShipSlot;
Modules.ResourceIdx BestGood = NullRIdx;
Modules.ResourceIdx NextBestGood = NullRIdx;
int HowMuchCanShip;
int RawShipArray[] = new int [PRCNumGoods];
int CandidateHolds;

//sp.MaxHoldGood = NullRIdx;
//sp.MaxHoldShip = ShipIdx.NotAShipSlot;
sp.SelectedGood = NullRIdx;
sp.PickedShip = ShipIdx.NotAShipSlot;

 for ( int iRAI = 0; iRAI < PRCNumGoods; iRAI++){
  Modules.ResourceIdx iRR = AIGameData.AIGDPriorityGoodShip[iRAI];
  int iR = iRR.ordinal();

  HowMuchCanShip = Integer.valueOf(PRFArray[Player].Text1[iR +
	  IndigoIdx.ordinal()].getText());
  RawShipArray[iR] = HowMuchCanShip;
  BestHolds[iR] = 0;
  BestShip[iR] = ShipIdx.NotAShipSlot;

  if ( HowMuchCanShip > 0 ) {
   HowManyKinds++;

   for (ShipIdx CandidateShip : EnumSet.range( ShipIdx.SmallShip , ShipIdx.LargeShip)){
    CandidateHolds = ShipsInPlay[CandidateShip.ordinal()].Capacity -
	    ShipsInPlay[CandidateShip.ordinal()].NumHoldsFull;
    if ( ShipsInPlay[CandidateShip.ordinal()].Good == iRR ) {
     BestShip[iR] = CandidateShip;
     if ( HowMuchCanShip > CandidateHolds ) 
      BestHolds[iR] = CandidateHolds;
     else
      BestHolds[iR] = HowMuchCanShip;
     
     BestGood = iRR;
     break;}
    else
    if ( ShipsInPlay[CandidateShip.ordinal()].Good == NullRIdx )
     if ( (BestHolds[iR] == 0) | ((BestHolds[iR] > 0) & (HowMuchCanShip > BestHolds[iR])) ) {
      BestShip[iR] = CandidateShip;
      if ( HowMuchCanShip > CandidateHolds ) 
       BestHolds[iR] = CandidateHolds;
      else
       BestHolds[iR] = HowMuchCanShip;
      
      BestGood = iRR;
     };
    
   };//Ship

  }; //none to ship

  if ( BestHolds[iR] > CurrentMax ) {
   CurrentMax = BestHolds[iR];
   sp.SelectedGood = BestGood;
   sp.PickedShip = BestShip[iR];
    if ( NextBestHolds == 0 ) {
     NextBestHolds = BestHolds[iR];
     NextBestShip = BestShip[iR];
     NextBestGood = iRR;
    };}
  else
  if ( BestHolds[iR] > NextBestHolds ) {
   NextBestHolds = BestHolds[iR];
   NextBestShip = BestShip[iR];
   NextBestGood = iRR;
  };
 };//Resource

 if ( Building.Production(PRGDCurrentPlayer, BuildingIdx.WharfIdx) > 0 ) {

  if ( HowManyKinds > 0 ) {//You have something left in your Docks
   if ( CurrentMax == 0 ) { //Nothing can go into ships
    HowMuchCanShip = 0;
    BestGood = NullRIdx;
    for (Modules.ResourceIdx iRR : EnumSet.range( Indigo , Coffee)){
     int iR = iRR.ordinal();
     if ( HowMuchCanShip < RawShipArray[iR] ) {
      HowMuchCanShip = RawShipArray[iR];
      BestGood = iRR;
     };
    }; 
    sp.SelectedGood = BestGood;
    sp.PickedShip = ShipIdx.WharfSlot; }
   else {//have a wharf but not last shipment so choose second highest
    sp.SelectedGood = NextBestGood;
    sp.PickedShip = NextBestShip;
   };
  }; //Nothing more to ship

 }; //No Wharf
 return sp;
};

public static int AggShipments(SetupPlayerNames sPlayer){
//AI uses this to determine Aggregate shipping (i.e. how many shipments x size)
int Player = sPlayer.ordinal();
boolean ShipFree[] = new boolean [PRCNumShips];
// ShipTypes BestShip;
// int NumShipments;
int NumGoods[] = new int [PRCNumGoods];
boolean GoodAvailable[] = new boolean [PRCNumGoods];
boolean EmptyShipFound;
boolean HasAWharf;
int CandidateHolds;
int ResIdx[] = new int [PRCNumGoods];
int AggShipments = 0;

 for ( int iSF = 0; iSF < PRCNumShips; iSF++){
  ShipFree[iSF] = true;
 };

 for (Modules.ResourceIdx RiR : EnumSet.range( Indigo , Coffee)){
  int iR = RiR.ordinal();
  NumGoods[iR] = Integer.valueOf(PRFArray[Player].Text1[iR + IndigoIdx.ordinal()].getText());
  if ( NumGoods[iR] > 0 ) 
   GoodAvailable[iR] = true;
  else
   GoodAvailable[iR] = false;
  
 };

// NumShipments = 0
 EmptyShipFound = false;
 HasAWharf = false;

 //Deal with Partial Ships first
 for (ShipIdx sCandidateShip : EnumSet.range( ShipIdx.SmallShip , ShipIdx.LargeShip)){
  int CandidateShip = sCandidateShip.ordinal();
  if ( ShipsInPlay[CandidateShip].Good == NullRIdx )
   EmptyShipFound = true;
  else {
   ShipFree[CandidateShip] = false;
   GoodAvailable[ShipsInPlay[CandidateShip].Good.ordinal()] = false;
   CandidateHolds = ShipsInPlay[CandidateShip].Capacity - ShipsInPlay[CandidateShip].NumHoldsFull;
   if ( CandidateHolds > 0 ) 
    if ( NumGoods[ShipsInPlay[CandidateShip].Good.ordinal()] > CandidateHolds )
     AggShipments = AggShipments + CandidateHolds;
    else
     AggShipments = AggShipments + NumGoods[ShipsInPlay[CandidateShip].Good.ordinal()];
  };
 }; 

 //};assign a number if there's a wharf
 if ( Building.Production(PRGDCurrentPlayer, BuildingIdx.WharfIdx) > 0 ) 
  HasAWharf = true;

 if ( HasAWharf | EmptyShipFound ) {
  //Sort Goods by number of barrels available
  ResIdx = Utilities.SortAscending(PRCNumGoods, NumGoods);
  if ( HasAWharf ) {
   for (int iR = PRCNumGoods - 1; iR>=0; iR--){
    if ( GoodAvailable[ResIdx[iR]]) {
     AggShipments = AggShipments + NumGoods[ResIdx[iR]];
     GoodAvailable[ResIdx[iR]] = false;
     break;
    };
   }; 
  };
 //Lastly assign a number for empty ships
  if ( EmptyShipFound ) {
   for (RevShipIdx rCandidateShip : EnumSet.range( RevShipIdx.LargeShipR , RevShipIdx.SmallShipR)){

    int CandidateShip = rCandidateShip.UnRev().ordinal();
    if ( ShipFree[CandidateShip])
     for (int iR = PRCMaxResourceIdx; iR >= 0; --iR) {
      if ( GoodAvailable[ResIdx[iR]] ) {
       if ( ShipsInPlay[CandidateShip].Capacity > NumGoods[ResIdx[iR]] )
        AggShipments = AggShipments + NumGoods[ResIdx[iR]];
       else
        AggShipments = AggShipments + ShipsInPlay[CandidateShip].Capacity;
       
       GoodAvailable[ResIdx[iR]] = false;
       break;
      };
    };
   };
  };
 };

return AggShipments;
};

public static ShipIdx ShipTypeToIdx (ShipTypes st) {
	
  if (st == ShipTypes.PlayerWharf)
   return ShipIdx.WharfSlot;

  if (st == ShipTypes.NoShip)
   return ShipIdx.NotAShipSlot;
	
  for (ShipIdx si  : EnumSet.range( ShipIdx.SmallShip , ShipIdx.LargeShip)){
   if (ShipsInPlay[si.ordinal()].Ship == st)
    return si;
  }

  return ShipIdx.NotAShipSlot;
}

public static void Initialise(ShipTypes sFirstShip ) {
 int FirstShip = sFirstShip.ordinal();
 ShipsInPlay[ShipIdx.SmallShip.ordinal()] = new PRShips();
 ShipsInPlay[ShipIdx.MedShip.ordinal()] = new PRShips();
 ShipsInPlay[ShipIdx.LargeShip.ordinal()]= new PRShips();
 ShipsInPlay[ShipIdx.SmallShip.ordinal()].Left = ShipsLeft.SmallShipLeft;
 ShipsInPlay[ShipIdx.MedShip.ordinal()].Left = ShipsLeft.MediumShipLeft;
 ShipsInPlay[ShipIdx.LargeShip.ordinal()].Left = ShipsLeft.LargeShipLeft;
 ShipsInPlay[ShipIdx.SmallShip.ordinal()].Name = "Small";
 ShipsInPlay[ShipIdx.MedShip.ordinal()].Name = "Medium";
 ShipsInPlay[ShipIdx.LargeShip.ordinal()].Name = "Large";

 for (ShipIdx si : EnumSet.range( ShipIdx.SmallShip , ShipIdx.LargeShip)){ //Pick which ships to use
  int i = si.ordinal();
  ShipsInPlay[i].Ship = ShipTypes.values()[ i + FirstShip]; //TODO:: Reverse Enum
  switch ( ShipsInPlay[i].Ship){
   case Ship4:
    ShipsInPlay[i].FirstHold = PRShipFirstHoldIdx.ship4First;
    ShipsInPlay[i].LastHold = PRShipLastHoldIdx.ship4Last;
    ShipsInPlay[i].Capacity = 4;
    break;
   case Ship5:
    ShipsInPlay[i].FirstHold = PRShipFirstHoldIdx.ship5First;
    ShipsInPlay[i].LastHold = PRShipLastHoldIdx.ship5Last;
    ShipsInPlay[i].Capacity = 5;
    break;
   case Ship6:
    ShipsInPlay[i].FirstHold = PRShipFirstHoldIdx.ship6First;
    ShipsInPlay[i].LastHold = PRShipLastHoldIdx.ship6Last;
    ShipsInPlay[i].Capacity = 6;
    break;
   case Ship7:
    ShipsInPlay[i].FirstHold = PRShipFirstHoldIdx.ship7First;
    ShipsInPlay[i].LastHold = PRShipLastHoldIdx.ship7Last;
    ShipsInPlay[i].Capacity = 7;
    break;
   case Ship8:
    ShipsInPlay[i].FirstHold = PRShipFirstHoldIdx.ship8First;
    ShipsInPlay[i].LastHold = PRShipLastHoldIdx.ship8Last;
    ShipsInPlay[i].Capacity = 8;
  };
  ShipsInPlay[i].Good = NullRIdx;
  ShipsInPlay[i].NumHoldsFull = 0;
 };

 if ( FirstShip > ShipTypes.Ship4.ordinal() ) { //Ship graphics are okay for the 3 player game already
// Clear the docks
  for (ShipTypes si : EnumSet.range( ShipTypes.Ship4 , ShipTypes.Ship8)){
   int i = si.ordinal();
   PRPuertoRico.playermat.Picture1[i].setVisible(false);
   PRPuertoRico.playermat.Picture1[i].setEnabled(false);
  }; 
  for (ShipIdx si  : EnumSet.range( ShipIdx.SmallShip , ShipIdx.LargeShip)){
   int i = si.ordinal();
   Object j1 =PRPuertoRico.playermat.Picture1[ShipsInPlay[i].Ship.ordinal()];
   int x = ShipsInPlay[i].Left.getX();
   PRPuertoRico.playermat.Picture1[ShipsInPlay[i].Ship.ordinal()].setLocation(
	   ShipsInPlay[i].Left.getX(), PRCShipTop);
   PRPuertoRico.playermat.Picture1[ShipsInPlay[i].Ship.ordinal()].setEnabled(true);
   PRPuertoRico.playermat.Picture1[ShipsInPlay[i].Ship.ordinal()].setVisible(true);
  };
 };

 GoodPickedToShip = NullRIdx;

// Resize next two arrays
 WhoFinishedShipping = (boolean[])Utilities.resizeArray(WhoFinishedShipping,PRGDActualNumPlayers);
 WharfUsed = (boolean[])Utilities.resizeArray(WharfUsed,PRGDActualNumPlayers);

 for (int iP = SetupPlayerNames.HumanPlayerSPN.ordinal(); iP < PRGDActualNumPlayers; iP++){
  WhoFinishedShipping[iP] = false;
  WharfUsed[iP] = false;
 }; 
 CaptainsPrivilegeTaken = false;
};

}
