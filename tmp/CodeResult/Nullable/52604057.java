package javax.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.annotation.meta.TypeQualifierNickname;
import javax.annotation.meta.When;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Nonnull(when=When.UNKNOWN)
@TypeQualifierNickname
public @interface Nullable
{
}

/* Location:           D:\Jervis\Documents\Programming\Research\Android\apks\com.google.android.apps.translate-141\classes_dex2jar.jar
 * Qualified Name:     javax.annotation.Nullable
 * JD-Core Version:    0.6.2
 */