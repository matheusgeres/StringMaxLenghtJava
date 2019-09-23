package idwall.desafio.string;

/**
 * Created by Rodrigo Catão Araujo on 06/02/2018.
 */
public abstract class StringFormatter {

    private Integer limit;
    private Boolean justify;

    public StringFormatter() {
        this.limit = 40;
        this.justify = false;
    }

    /**
     * It receives a text and should return it formatted
     *
     * @param text
     * @return
     */
    public abstract String format(String text);

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Boolean getJustify() {
        return justify;
    }

    public void setJustify(Boolean justify) {
        this.justify = justify;
    }
}
