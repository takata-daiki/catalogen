/**
 * Field.java
 * 
 * @Author
 *   Yuki Kawata
 */

package wiz.project.jan;



/**
 * 場
 */
public final class Field {
    
    /**
     * コンストラクタを自分自身に限定許可
     */
    private Field() {
    }
    
    
    
    /**
     * インスタンスを取得
     * 
     * @return インスタンス。
     */
    public static Field getInstance() {
        return INSTANCE;
    }
    
    
    
    /**
     * 場風を取得
     * 
     * @return 場風。
     */
    public Wind getWind() {
        synchronized (_WIND_LOCK) {
            return _wind;
        }
    }
    
    /**
     * 場風を設定
     * 
     * @param wind 場風。
     */
    public void setWind(final Wind wind) {
        synchronized (_WIND_LOCK) {
            if (wind != null) {
                _wind = wind;
            }
            else {
                _wind = Wind.TON;
            }
        }
    }
    
    
    
    /**
     * 自分自身のインスタンス
     */
    private static final Field INSTANCE = new Field();
    
    
    
    /**
     * ロックオブジェクト
     */
    private final Object _WIND_LOCK = new Object();
    
    
    
    /**
     * 場風
     */
    private Wind _wind = Wind.TON;
    
}

