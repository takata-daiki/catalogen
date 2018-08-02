package by.q64.promo.excelgen.constructor.entity.properties;

import by.q64.promo.excelgen.service.utils.FunctionDoubleArgs;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by Pavel on 01.10.2014.
 */
public class AbstractColumnProperties<N> extends ColumnProperties<N> {
    private Function<N, Map<String, String>> sourceMapFunc;

    public AbstractColumnProperties(Function<N, Map<String, String>> sourceMapFunc, List<AdvancedColumnProperties<N>> children) {
        super(null, (List)children);
        setAbstractProperty(true);
        this.sourceMapFunc = sourceMapFunc;
    }

    public Map<String, String> getSource(N n){
        return sourceMapFunc.apply(n);
    }
}
