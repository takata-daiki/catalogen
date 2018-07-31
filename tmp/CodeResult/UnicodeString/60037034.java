package org.codecop.keystrokes.mac;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;

import org.codecop.keystrokes.mac.carbon.Carbon;
import org.codecop.keystrokes.mac.carbon.ICarbon;
import org.codecop.keystrokes.mac.corefoundation.CoreFoundation;
import org.codecop.keystrokes.mac.corefoundation.ICoreFoundation;
import org.codecop.keystrokes.mac.corefoundation.ICoreFoundation.CFStringRef;
import org.codecop.keystrokes.mac.coreservices.CoreServices;
import org.codecop.keystrokes.mac.coreservices.ICoreServices;

/**
 * Resolve a given virtual key code to a string based on the current keyboard layout.
 * 
 * @author brachwitz
 */
public class KeyCodeResolver {

   private ICarbon carbon = new Carbon();
   private ICoreFoundation cf = new CoreFoundation();
   private ICoreServices coreservices = new CoreServices();

   public String resolve(short keyCode) {
      Pointer currentKeyboard = carbon.TISCopyCurrentKeyboardInputSource();
      if (currentKeyboard == null) {
         throw new IllegalStateException("no keyboard source available");
      }
      Pointer unicodeLayouData = carbon.TISGetInputSourceProperty(currentKeyboard, CFStringRef.from("TISPropertyUnicodeKeyLayoutData"));
      if (unicodeLayouData == null) {
         throw new IllegalStateException("no keyboard layout data available");
      }
      Pointer keyboardLayout = cf.CFDataGetBytePtr(unicodeLayouData);
      if (keyboardLayout == null) {
         throw new IllegalStateException("no keyboard layout available");
      }
      IntByReference deadKeyState = new IntByReference();
      long maxStringLength = 255;
      LongByReference actualStringLength = new LongByReference();
      short[] unicodeString = new short[(int) maxStringLength];
      coreservices.UCKeyTranslate(keyboardLayout, keyCode, CoreServices.kUCKeyActionDown, 0, carbon.LMGetKbdType(), 0, deadKeyState,
            maxStringLength, actualStringLength, unicodeString);

      return new String(toIntArray(unicodeString), 0, (int) actualStringLength.getValue());
   }

   private int[] toIntArray(short[] unicodeString) {
      int[] res = new int[unicodeString.length];
      for (int i = 0; i < res.length; i++) {
         res[i] = unicodeString[i];
      }
      return res;
   }

}
