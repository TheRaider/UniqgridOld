package Libraries;

import android.content.Context;

/**
 * Author: andy
 * Date: 16/1/21 12:07
 * E-mail: andyxialm@gmail.com
 * Description: Tools
 */
public class CompatUtils {
    public  static  int  dp2px ( Context  context , float  dipValue ) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}