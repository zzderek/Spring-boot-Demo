package com.y2t.akeso.validator;


import com.y2t.akeso.annotation.MobileNumber;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneCustomizeValidator implements ConstraintValidator<MobileNumber, String> {
    /**
     * 最新的大陆手机号码匹配规则见：
     * https://github.com/VincentSit/ChinaMobilePhoneNumberRegex/blob/master/README-CN.md
     */
    private String phoneRegex = "^(?:\\+?86)?1(?:3\\d{3}|5[^4\\D]\\d{2}|8\\d{3}|7(?:[0-35-9]\\d{2}|4(?:0\\d|1[0-2]|9\\d))|9[0-35-9]\\d{2}|6[2567]\\d{2}|4[579]\\d{2})\\d{6}$";
    private Pattern phonePattern = Pattern.compile(phoneRegex);

    @Override
    public void initialize(MobileNumber constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return Boolean.FALSE;
        }
        return phonePattern.matcher(value).matches();
    }
}
