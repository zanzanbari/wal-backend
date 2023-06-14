package backend.wal.subtitle;

import java.util.Arrays;

public enum Subtitle {

    ONE("가끔은 쉬어가는 것도 좋아~ 넌 항상 최고야!"),
    TWO("박수를 3번 쳐보자. 오늘 하루도 화이팅!"),
    THREE("안녕! 오늘 뭔가 느낌이 좋아!"),
    FOUR("유후~ 난 춤을  추면 기분이 좋아져"),
    FIVE("야호! 오늘도 즐거운 하루~ 좋아좋아"),
    SIX("다들 밥 잘 먹어! 난 뼈다구가 젤루 좋아"),
    SEVEN("오늘은 어떤 맛도리를 먹어볼까? 벌써 기대된다"),
    EIGHT("오늘은 내가 좋아하는 날씨야"),
    NINE("밥은 먹었어? 나는 먹었어!"),
    TEN("뭐가 필요해! 왈뿡이만 믿어!"),

    ELEVEN("무슨 일인지는 모르겠지만 걱정 말라구!"),
    TWELVE("또 왈뿡이 보고 싶어서 온거야?"),
    THIRTEEN("왈뿡이 심심해… 같이 놀자~"),
    FOURTEEN("즐거운 하루 보내~ 뿡!"),
    FIFTEEN("산책하기 좋은 날씨다 밖에 나가자!"),
    SIXTEEN("맑은 하늘처럼 예쁜 하루가 되면 좋겠다!"),
    SEVENTEEN("찬바람을 이겨내 보자! 뿡뿡"),
    EIGHTEEN("오늘은 또 어떤 일이 생길까 기대되지 않아?"),
    NINETEEN("오늘 뭔가 좋은 꿈을 꿀 것 같아"),
    TWENTY("행복한 일은 매일 있어"),

    TWENTY_ONE("뿡뿡! 누가 뭐래도 너가 짱이야!"),
    TWENTY_TWO("자 따라해, 난 너무 귀여워! 난 최고야!"),
    TWENTY_THREE("오늘도 힘차게 시작해볼까?"),
    TWENTY_FOUR("오늘도 왈뿡이를 찾아줘서 고마워. 또 보자!"),
    TWENTY_FIVE("산책을 한번 해볼까? 왈뿡이랑 같이 나가자!"),
    TWENTY_SIX("좋아. 가보자고! 멍!"),
    TWENTY_SEVEN("더 나은 하루를 위해 더 나답게 살아보자!"),
    TWENTY_EIGHT("일단, 신발 신고 나가볼까? "),
    TWENTY_NINE("일단 무작정 시작해볼까?"),
    THIRTY("비타민이랑 영양제 챙겨먹었어?"),

    THIRTY_ONE("오케이, 오늘 저녁은 외식이다."),
    THIRTY_TWO("왔어? 왈뿡이보러 또 올거지?"),
    THIRTY_THREE("언제든지 와줘 난 항상 여기 있어"),
    THIRTY_FOUR("왈뿡이 보고 오늘 하루도 화이팅이야!"),
    THIRTY_FIVE("오늘은 어떤 달이 뜰까 기대된다! 두근두근"),
    THIRTY_SIX("어제보다 더 좋을 오늘을 왈뿡이가 응원해!"),

    ERROR("서브타이틀 에러를 본 당신은 행운아!"),
    ;

    private final String value;

    Subtitle(final String value) {
        this.value = value;
    }

    public static String getRandomValue(int randomNumber) {
        return Arrays.stream(values())
                .filter(subtitle -> subtitle.ordinal() == randomNumber)
                .findFirst()
                .orElse(ERROR)
                .value;
    }
}
