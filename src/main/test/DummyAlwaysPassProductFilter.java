import cz.muni.fi.pv260.productfilter.Filter;
import cz.muni.fi.pv260.productfilter.Product;

public class DummyAlwaysPassProductFilter implements Filter<Product> {

    @Override
    public boolean passes(Product item) {
        return true;
    }
}
