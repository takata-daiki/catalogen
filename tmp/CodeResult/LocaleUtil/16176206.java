/*
 * Copyright (c) 2002-2012 Alibaba Group Holding Limited.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.citrus.util.i18n;

import static com.alibaba.citrus.util.BasicConstant.*;
import static com.alibaba.citrus.util.CollectionUtil.*;
import static com.alibaba.citrus.util.StringUtil.*;

import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.EventListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.alibaba.citrus.util.ClassLoaderUtil;
import com.alibaba.citrus.util.StringUtil;
import com.alibaba.citrus.util.io.StreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ????????????????
 * <p>
 * ????locale?charset?????????????????????????????????????????
 * <code>LocaleUtil</code>??????????????locale?charset?
 * </p>
 * <p>
 * <code>LocaleUtil</code>???????????locale/charset???
 * </p>
 * <ul>
 * <li>???????JVM??????????????JVM???????????<code>LocaleUtil.getSystem()</code>
 * ???</li>
 * <li>?????????JVM??????????????<code>LocaleUtil.getDefault()</code>
 * ????????????????????</li>
 * <li>?????????????????????????<code>LocaleUtil.getContext()</code>
 * ???????????????????????????????locale?charset????????????</li>
 * </ul>
 * <p>
 * Util??????????????????<code>LocaleUtil.getContext()</code>
 * ??????locale?charset??????<code>StringEscapeUtil.escapeURL(value)</code>
 * ?????charset
 * ???context???charset????????????context????????????????????locale?charset???
 * </p>
 *
 * @author Michael Zhou
 */
public class LocaleUtil {
    private static final LocaleInfo              systemLocaleInfo        = new LocaleInfo();
    private static       LocaleInfo              defaultLocalInfo        = systemLocaleInfo;
    private static final ThreadLocal<LocaleInfo> contextLocaleInfoHolder = new ThreadLocal<LocaleInfo>();

    /**
     * ??locale??????
     *
     * @param locale ????locale
     */
    public static boolean isLocaleSupported(Locale locale) {
        return locale != null && AvailableLocalesLoader.locales.AVAILABLE_LANGUAGES.contains(locale.getLanguage())
               && AvailableLocalesLoader.locales.AVAILABLE_COUNTRIES.contains(locale.getCountry());
    }

    /**
     * ?????charset??????
     *
     * @param charset ????charset
     */
    public static boolean isCharsetSupported(String charset) {
        return charset != null && Charset.isSupported(charset);
    }

    /**
     * ??locale????
     * <p>
     * Locale???????????<code>language_country_variant</code>?
     * </p>
     *
     * @param localeString ???????
     * @return <code>Locale</code>?????locale?????????<code>null</code>
     */
    public static Locale parseLocale(String localeString) {
        localeString = trimToNull(localeString);

        if (localeString == null) {
            return null;
        }

        String language = EMPTY_STRING;
        String country = EMPTY_STRING;
        String variant = EMPTY_STRING;

        // language
        int start = 0;
        int index = localeString.indexOf("_");

        if (index >= 0) {
            language = localeString.substring(start, index).trim();

            // country
            start = index + 1;
            index = localeString.indexOf("_", start);

            if (index >= 0) {
                country = localeString.substring(start, index).trim();

                // variant
                variant = localeString.substring(index + 1).trim();
            } else {
                country = localeString.substring(start).trim();
            }
        } else {
            language = localeString.substring(start).trim();
        }

        return new Locale(language, country, variant);
    }

    /**
     * ??????????, ??????????, ???<code>UnsupportedEncodingException</code>.
     *
     * @param charset ?????
     * @return ????????
     * @throws IllegalCharsetNameException ???????????
     * @throws UnsupportedCharsetException ??????????
     */
    public static String getCanonicalCharset(String charset) {
        return Charset.forName(charset).name();
    }

    /**
     * ?????resource bundle????????
     * <p>
     * ???
     * <code>calculateBundleNames("hello.jsp", new Locale("zh", "CN", "variant"))</code>
     * ????????
     * <ol>
     * <li>hello_zh_CN_variant.jsp</li>
     * <li>hello_zh_CN.jsp</li>
     * <li>hello_zh.jsp</li>
     * <li>hello.jsp</li>
     * </ol>
     * </p>
     *
     * @param baseName bundle????
     * @param locale   ????
     * @return ?????bundle?
     */
    public static List<String> calculateBundleNames(String baseName, Locale locale) {
        return calculateBundleNames(baseName, locale, false);
    }

    /**
     * ?????resource bundle????????
     * <p>
     * ???
     * <code>calculateBundleNames("hello.jsp", new Locale("zh", "CN", "variant"),
     * false)</code>????????
     * <ol>
     * <li>hello_zh_CN_variant.jsp</li>
     * <li>hello_zh_CN.jsp</li>
     * <li>hello_zh.jsp</li>
     * <li>hello.jsp</li>
     * </ol>
     * </p>
     * <p>
     * ?<code>noext</code>?<code>true</code>???????????
     * <code>calculateBundleNames("hello.world",
     * new Locale("zh", "CN", "variant"), true)</code>????????
     * <ol>
     * <li>hello.world_zh_CN_variant</li>
     * <li>hello.world_zh_CN</li>
     * <li>hello.world_zh</li>
     * <li>hello.world</li>
     * </ol>
     * </p>
     *
     * @param baseName bundle????
     * @param locale   ????
     * @return ?????bundle?
     */
    public static List<String> calculateBundleNames(String baseName, Locale locale, boolean noext) {
        baseName = StringUtil.trimToEmpty(baseName);

        if (locale == null) {
            locale = new Locale(EMPTY_STRING);
        }

        // ????
        String ext = EMPTY_STRING;
        int extLength = 0;

        if (!noext) {
            int extIndex = baseName.lastIndexOf(".");

            if (extIndex != -1) {
                ext = baseName.substring(extIndex, baseName.length());
                extLength = ext.length();
                baseName = baseName.substring(0, extIndex);

                if (extLength == 1) {
                    ext = EMPTY_STRING;
                    extLength = 0;
                }
            }
        }

        // ??locale???
        LinkedList<String> result = createLinkedList();
        String language = locale.getLanguage();
        int languageLength = language.length();
        String country = locale.getCountry();
        int countryLength = country.length();
        String variant = locale.getVariant();
        int variantLength = variant.length();

        StringBuilder buffer = new StringBuilder(baseName);

        buffer.append(ext);
        result.addFirst(buffer.toString());
        buffer.setLength(buffer.length() - extLength);

        // ??locale?("", "", "").
        if (languageLength + countryLength + variantLength == 0) {
            return result;
        }

        // ??baseName_language???baseName??????????
        if (buffer.length() > 0) {
            buffer.append('_');
        }

        buffer.append(language);

        if (languageLength > 0) {
            buffer.append(ext);
            result.addFirst(buffer.toString());
            buffer.setLength(buffer.length() - extLength);
        }

        if (countryLength + variantLength == 0) {
            return result;
        }

        // ??baseName_language_country
        buffer.append('_').append(country);

        if (countryLength > 0) {
            buffer.append(ext);
            result.addFirst(buffer.toString());
            buffer.setLength(buffer.length() - extLength);
        }

        if (variantLength == 0) {
            return result;
        }

        // ??baseName_language_country_variant
        buffer.append('_').append(variant);

        buffer.append(ext);
        result.addFirst(buffer.toString());
        buffer.setLength(buffer.length() - extLength);

        return result;
    }

    /**
     * ????????????
     *
     * @return ?????????
     */
    public static LocaleInfo getSystem() {
        return systemLocaleInfo;
    }

    /**
     * ????????
     *
     * @return ?????
     */
    public static LocaleInfo getDefault() {
        return defaultLocalInfo == null ? systemLocaleInfo : defaultLocalInfo;
    }

    /**
     * ????????
     *
     * @param locale ??
     * @return ???????
     */
    public static LocaleInfo setDefault(Locale locale) {
        LocaleInfo old = getDefault();
        setDefaultAndNotify(new LocaleInfo(locale, null, systemLocaleInfo));
        return old;
    }

    /**
     * ????????
     *
     * @param locale  ??
     * @param charset ?????
     * @return ???????
     */
    public static LocaleInfo setDefault(Locale locale, String charset) throws UnsupportedCharsetException {
        LocaleInfo old = getDefault();
        setDefaultAndNotify(new LocaleInfo(locale, charset, systemLocaleInfo));
        return old;
    }

    /**
     * ????????
     *
     * @param localeInfo ??????????
     * @return ???????
     */
    public static LocaleInfo setDefault(LocaleInfo localeInfo) throws UnsupportedCharsetException {
        if (localeInfo == null) {
            return setDefault(null, null);
        } else {
            LocaleInfo old = getDefault();
            setDefaultAndNotify(localeInfo);
            return old;
        }
    }

    private static void setDefaultAndNotify(LocaleInfo localeInfo) throws UnsupportedCharsetException {
        defaultLocalInfo = localeInfo.assertCharsetSupported();

        for (Notifier notifier : notifiers) {
            notifier.defaultChanged(localeInfo);
        }
    }

    /** ?????????? */
    public static void resetDefault() {
        defaultLocalInfo = systemLocaleInfo;

        for (Notifier notifier : notifiers) {
            notifier.defaultReset();
        }
    }

    /**
     * ????thread??????
     *
     * @return ??thread?????
     */
    public static LocaleInfo getContext() {
        LocaleInfo contextLocaleInfo = contextLocaleInfoHolder.get();
        return contextLocaleInfo == null ? getDefault() : contextLocaleInfo;
    }

    /**
     * ????thread??????
     *
     * @param locale ??
     * @return ???thread?????
     */
    public static LocaleInfo setContext(Locale locale) {
        LocaleInfo old = getContext();
        setContextAndNotify(new LocaleInfo(locale, null, defaultLocalInfo));
        return old;
    }

    /**
     * ????thread??????
     *
     * @param locale  ??
     * @param charset ?????
     * @return ???thread?????
     */
    public static LocaleInfo setContext(Locale locale, String charset) throws UnsupportedCharsetException {
        LocaleInfo old = getContext();
        setContextAndNotify(new LocaleInfo(locale, charset, defaultLocalInfo));
        return old;
    }

    /**
     * ????thread??????
     *
     * @param localeInfo ??????????
     * @return ???thread?????
     */
    public static LocaleInfo setContext(LocaleInfo localeInfo) throws UnsupportedCharsetException {
        if (localeInfo == null) {
            return setContext(null, null);
        } else {
            LocaleInfo old = getContext();
            setContextAndNotify(localeInfo);
            return old;
        }
    }

    private static void setContextAndNotify(LocaleInfo localeInfo) throws UnsupportedCharsetException {
        contextLocaleInfoHolder.set(localeInfo.assertCharsetSupported());

        for (Notifier notifier : notifiers) {
            notifier.contextChanged(localeInfo);
        }
    }

    /** ????thread?????? */
    public static void resetContext() {
        contextLocaleInfoHolder.remove();

        for (Notifier notifier : notifiers) {
            notifier.contextReset();
        }
    }

    private static Logger     log       = LoggerFactory.getLogger(LocaleUtil.class);
    private static Notifier[] notifiers = getNotifiers();

    private static Notifier[] getNotifiers() {
        try {
            URL[] files = ClassLoaderUtil.getResources("META-INF/services/localeNotifiers", ClassLoaderUtil.class);
            List<Notifier> list = createLinkedList();

            for (URL file : files) {
                for (String className : StringUtil
                        .split(StreamUtil.readText(file.openStream(), "UTF-8", true), "\r\n ")) {
                    list.add(Notifier.class.cast(ClassLoaderUtil.newInstance(className, ClassLoaderUtil.class)));
                }
            }

            return list.toArray(new Notifier[list.size()]);
        } catch (Exception e) {
            log.warn("Failure in LocaleUtil.getNotifiers()", e);
            return new Notifier[0];
        }
    }

    /** ?default?context locale??????????? */
    public interface Notifier extends EventListener {
        void defaultChanged(LocaleInfo newValue);

        void defaultReset();

        void contextChanged(LocaleInfo newValue);

        void contextReset();
    }

    /** ??????????????? */
    private static class AvailableLocalesLoader {
        private static final AvailableLocales locales = new AvailableLocales();
    }

    private static class AvailableLocales {
        private final Set<String> AVAILABLE_LANGUAGES = createHashSet();
        private final Set<String> AVAILABLE_COUNTRIES = createHashSet();

        private AvailableLocales() {
            Locale[] availableLocales = Locale.getAvailableLocales();

            for (Locale locale : availableLocales) {
                AVAILABLE_LANGUAGES.add(locale.getLanguage());
                AVAILABLE_COUNTRIES.add(locale.getCountry());
            }
        }
    }
}
