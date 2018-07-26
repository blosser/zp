package ru.ittrans.zp.io;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 13.10.2010
 * Time: 13:13:24
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "ZP_MAN")
public class ZpMan extends ZpAbstract
{
    public static final Long TWO = 2l;
    public static final Long ONE = 1l;
    public static final Long ZERO = 0l;
    public static final Long ALL = -1l;

    public static final Long DOGOVOR_NONE = 0l;
    public static final Long DOGOVOR_ACTIVE = 1l;
    public static final Long DOGOVOR_END = 2l;
    public static final Long DOGOVOR_END_SOON = 3l;

    private Long id;
    private String name1;
    private String name2;
    private String name3;
    private String phone;
    private String tabNumber;
    private String tabNumber2;
    private Date birthday;
    private Long secondJob;
    private Long active;
    private String passportNumber;
    private String pensionNumber;
    private Long blackList;
    private Long shopId;
    private Long dogovor;
    private String passportAddress;
    private String regAddress;
    private String passportWhere;
    private String inn;
    private String manScore;
    private String comment;
    private Long card;
    private Long pZdm;
    private Long pTvp;
    private Long pSk;
    private Long pSb;
    private Date pSbDate;
    private Long pDog;
    private Date pDogDate;
    private Long specMobile;
    private Long mobile;
    private Long zlo;

    private List<ZpOwnerMan> ownerManList;

    @Transient
    public List<String> l122010;
    @Transient
    public List<String> l012011;
    @Transient
    public List<String> l022011;
    @Transient
    public List<String> l032011;
    @Transient
    public List<String> l042011;

    private List<ZpManValue> mvList;
    //для импорта
    private List<ZpManValue> mvPlanList;
    private List<ZpManValue> mvFactList;

    private ZpManPaid manPaid;
    private Long sessionId;

    private String status;

    public ZpMan()
    {
    }

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName1()
    {
        return name1;
    }

    public void setName1(String name1)
    {
        this.name1 = name1;
    }

    public String getName2()
    {
        return name2;
    }

    public void setName2(String name2)
    {
        this.name2 = name2;
    }

    public String getName3()
    {
        return name3;
    }

    public void setName3(String name3)
    {
        this.name3 = name3;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    @Column(name = "TAB_NUMBER")
    public String getTabNumber()
    {
        return tabNumber;
    }

    public void setTabNumber(String tabNumber)
    {
        this.tabNumber = tabNumber;
    }

    @Column(name = "TAB_NUMBER2")
    public String getTabNumber2()
    {
        return tabNumber2;
    }

    public void setTabNumber2(String tabNumber2)
    {
        this.tabNumber2 = tabNumber2;
    }

    public Date getBirthday()
    {
        return birthday;
    }

    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

    @Column(name = "SECOND_JOB")
    public Long getSecondJob()
    {
        return secondJob;
    }

    public void setSecondJob(Long secondJob)
    {
        this.secondJob = secondJob;
    }

    public Long getActive()
    {
        return active;
    }

    public void setActive(Long active)
    {
        this.active = active;
    }

    @Transient
    public String getName()
    {
        return getName1();
    }

    @Transient
    public List<ZpOwnerMan> getOwnerManList()
    {
        return ownerManList;
    }

    public void setOwnerManList(List<ZpOwnerMan> ownerManList)
    {
        this.ownerManList = ownerManList;
    }

    @Transient
    public List<ZpManValue> getMvList()
    {
        return mvList;
    }

    public void setMvList(List<ZpManValue> mvList)
    {
        this.mvList = mvList;
    }

    @Transient
    public List<ZpManValue> getMvPlanList()
    {
        return mvPlanList;
    }

    public void setMvPlanList(List<ZpManValue> mvPlanList)
    {
        this.mvPlanList = mvPlanList;
    }

    @Transient
    public List<ZpManValue> getMvFactList()
    {
        return mvFactList;
    }

    public void setMvFactList(List<ZpManValue> mvFactList)
    {
        this.mvFactList = mvFactList;
    }

    @Transient
    public ZpManPaid getManPaid()
    {
        return manPaid;
    }

    public void setManPaid(ZpManPaid manPaid)
    {
        this.manPaid = manPaid;
    }

    @Column(name = "PASSPORT_NUMBER")
    public String getPassportNumber()
    {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber)
    {
        this.passportNumber = passportNumber;
    }

    @Column(name = "PENSION_NUMBER")
    public String getPensionNumber()
    {
        return pensionNumber;
    }

    public void setPensionNumber(String pensionNumber)
    {
        this.pensionNumber = pensionNumber;
    }

    @Column(name = "BLACK_LIST")
    public Long getBlackList()
    {
        return blackList;
    }

    public void setBlackList(Long blackList)
    {
        this.blackList = blackList;
    }

    @Column(name = "SHOP_ID")
    public Long getShopId()
    {
        return shopId;
    }

    public void setShopId(Long shopId)
    {
        this.shopId = shopId;
    }

    @Transient
    public Long getDogovor()
    {
        return dogovor;
    }

    public void setDogovor(Long dogovor)
    {
        this.dogovor = dogovor;
    }

    @Column(name = "PASSPORT_ADDRESS")
    public String getPassportAddress()
    {
        return passportAddress;
    }

    public void setPassportAddress(String passportAddress)
    {
        this.passportAddress = passportAddress;
    }

    @Column(name = "REG_ADDRESS")
    public String getRegAddress()
    {
        return regAddress;
    }

    public void setRegAddress(String regAddress)
    {
        this.regAddress = regAddress;
    }

    @Column(name = "PASSPORT_WHERE")
    public String getPassportWhere()
    {
        return passportWhere;
    }

    public void setPassportWhere(String passportWhere)
    {
        this.passportWhere = passportWhere;
    }

    public String getInn()
    {
        return inn;
    }

    public void setInn(String inn)
    {
        this.inn = inn;
    }

    @Column(name = "SESSION_ID")
    public Long getSessionId()
    {
        return sessionId;
    }

    public void setSessionId(Long sessionId)
    {
        this.sessionId = sessionId;
    }

    @Column(name = "MAN_SCORE")
    public String getManScore()
    {
        return manScore;
    }

    public void setManScore(String manScore)
    {
        this.manScore = manScore;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public Long getCard()
    {
        return card;
    }

    public void setCard(Long card)
    {
        this.card = card;
    }

    @Transient
    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Column(name = "P_ZDM")
    public Long getpZdm()
    {
        return pZdm;
    }

    public void setpZdm(Long pZdm)
    {
        this.pZdm = pZdm;
    }

    @Column(name = "P_TVP")
    public Long getpTvp()
    {
        return pTvp;
    }

    public void setpTvp(Long pTvp)
    {
        this.pTvp = pTvp;
    }

    @Column(name = "P_SK")
    public Long getpSk()
    {
        return pSk;
    }

    public void setpSk(Long pSk)
    {
        this.pSk = pSk;
    }

    @Column(name = "P_SB")
    public Long getpSb()
    {
        return pSb;
    }

    public void setpSb(Long pSb)
    {
        this.pSb = pSb;
    }

    @Column(name = "P_SB_DATE")
    public Date getpSbDate()
    {
        return pSbDate;
    }

    public void setpSbDate(Date pSbDate)
    {
        this.pSbDate = pSbDate;
    }

    @Column(name = "P_DOG")
    public Long getpDog()
    {
        return pDog;
    }

    public void setpDog(Long pDog)
    {
        this.pDog = pDog;
    }

    @Column(name = "P_DOG_DATE")
    public Date getpDogDate()
    {
        return pDogDate;
    }

    public void setpDogDate(Date pDogDate)
    {
        this.pDogDate = pDogDate;
    }

    @Column(name = "SPEC_MOBILE")
    public Long getSpecMobile()
    {
        return specMobile;
    }

    public void setSpecMobile(Long specMobile)
    {
        this.specMobile = specMobile;
    }

    public Long getMobile()
    {
        return mobile;
    }

    public void setMobile(Long mobile)
    {
        this.mobile = mobile;
    }

    public Long getZlo()
    {
        return zlo;
    }

    public void setZlo(Long zlo)
    {
        this.zlo = zlo;
    }
}
