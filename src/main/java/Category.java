import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Category {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("products")
    private List<Product> products = new ArrayList<Product>();
    @JsonProperty("title")
    private String title;

}