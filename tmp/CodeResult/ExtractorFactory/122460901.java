/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package features;

import java.util.HashMap;

/**
 *
 * @author mjbrooks
 */
public class ExtractorFactory {

    private static boolean _initialized = false;

    public static void initialize() throws ClassNotFoundException {
        Class.forName("features.CountingFeatures");
        _initialized = true;
    }

    public static boolean isInitialized() {
        return _initialized;
    }
    static HashMap<String, FeatureExtractor> extractors = new HashMap<String, FeatureExtractor>();

    protected static void registerExtractor(String feature, FeatureExtractor extractor) {
        extractors.put(feature, extractor);
    }

    public static FeatureExtractor getExtractor(String feature) throws ClassNotFoundException {
        if (!isInitialized()) {
            initialize();
        }

        return extractors.get(feature);
    }
}
