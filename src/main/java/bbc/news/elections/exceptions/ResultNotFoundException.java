package bbc.news.elections.exceptions;


public class ResultNotFoundException extends RuntimeException {
    private Integer resultId;

    public ResultNotFoundException(Integer resultId) {
        this.resultId = resultId;
    }

    public ResultNotFoundException() {

    }

    public Integer getId() {
        return resultId;
    }
}
