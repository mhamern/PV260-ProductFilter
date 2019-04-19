import cz.muni.fi.pv260.productfilter.Filter;

public class DummyAlwaysPassFilter implements Filter<DummyValue> {
    @Override
    public boolean passes(DummyValue item) {
        return true;
    }
}
