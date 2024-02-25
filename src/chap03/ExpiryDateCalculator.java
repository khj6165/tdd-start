package chap03;

import java.time.LocalDate;
import java.time.YearMonth;

public class ExpiryDateCalculator {
    public LocalDate calculateExpiryDate(PayData payData){
        int addedMonths = calculateAddedMonths(payData.getPayAmount());
        if(payData.getFirstBillingDate() != null){
            return expiryDateUsingFirstBillingDate(payData, addedMonths);
        } else {
            return payData.getBillingDate().plusMonths(addedMonths);
        }
    }

    private LocalDate expiryDateUsingFirstBillingDate(PayData payData, int addedMonths){
        LocalDate candidateExp = payData.getBillingDate().plusMonths(addedMonths);
        if(isSameDayOfMonths(payData.getFirstBillingDate(),candidateExp)){
            final int dayLenOfCandiMon = lastDayOfMonth(candidateExp);
            final int dayOfFirstBilling = payData.getFirstBillingDate().getDayOfMonth();
            if(dayLenOfCandiMon < dayOfFirstBilling){
                return candidateExp.withDayOfMonth(dayLenOfCandiMon);
            }
            return candidateExp.withDayOfMonth(dayOfFirstBilling);
        } else{
            return candidateExp;
        }
    }

    private int calculateAddedMonths(int payAmount){
        if (payAmount >= 100_000){
            if(payAmount == 100_000) {
                return 12;
            } else{
                int yearAmount = payAmount / 100_000;
                return yearAmount * 12 + ((payAmount - yearAmount*100_000) / 10_000);
            }
        } else{
            return payAmount / 10_000;
        }
    }

    private boolean isSameDayOfMonths(LocalDate date1, LocalDate date2){
        if(date1.getDayOfMonth() != date2.getDayOfMonth()){
            return true;
        } else{
            return false;
        }
    }

    private int lastDayOfMonth(LocalDate date){
        return YearMonth.from(date).lengthOfMonth();
    }
}
