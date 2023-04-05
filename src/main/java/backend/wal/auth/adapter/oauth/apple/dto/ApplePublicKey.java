package backend.wal.auth.adapter.oauth.apple.dto;

public class ApplePublicKey {

    private String alg;  // 토큰을 암호화하는 데 사용되는 암호화 알고리즘
    private String e;    // RSA 공개 키의 지수 값
    private String kid;  // 개발자 계정에서 얻은 10자리 식별자 키
    private String kty;  // 키 유형 매개변수 설정 : "RSA"로 설정해야함
    private String n;    // RSA 공개 키의 모듈러스 값
    private String use;  // 공개 키의 의도된 용도

    private ApplePublicKey() {
    }

    public ApplePublicKey(final String alg, final String e, final String kid,
                          final String kty, final String n, final String use) {
        this.alg = alg;
        this.e = e;
        this.kid = kid;
        this.kty = kty;
        this.n = n;
        this.use = use;
    }

    public String getAlg() {
        return alg;
    }

    public String getE() {
        return e;
    }

    public String getKid() {
        return kid;
    }

    public String getKty() {
        return kty;
    }

    public String getN() {
        return n;
    }
}
