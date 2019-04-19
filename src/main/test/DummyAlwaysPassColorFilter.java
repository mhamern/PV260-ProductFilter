import cz.muni.fi.pv260.productfilter.Color;
import cz.muni.fi.pv260.productfilter.Filter;

public class DummyAlwaysPassColorFilter implements Filter<Color> {
    @Override
    public boolean passes(Color item) {
        return true;
    }
}
