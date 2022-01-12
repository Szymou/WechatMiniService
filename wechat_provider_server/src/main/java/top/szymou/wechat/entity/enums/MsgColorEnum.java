package top.szymou.wechat.entity.enums;

public enum MsgColorEnum {
    Blue("#102b6a")
    ,Red("#aa2116")
    ,Green("#7fb80e")
    ,Pink("#c77eb5")
    ,White("#fffffb")
    ,Black("#130c0e")
    ,Grey("#77787b")
    ,Yellow("#ffd400")

    ;

    private String code;

    MsgColorEnum(String code){
        this.code = code;
    }
}
