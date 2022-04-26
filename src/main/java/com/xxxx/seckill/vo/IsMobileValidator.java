package com.xxxx.seckill.vo;

import com.xxxx.seckill.utils.ValidatoUtil;
import com.xxxx.seckill.validator.IsMobile;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsMobileValidator implements ConstraintValidator<IsMobile,String> {

    private boolean required = false;
    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(required)
        {
            return ValidatoUtil.isMobile(s);
        }
        else
        {
            if(StringUtils.isEmpty(s)) return  true;
            else return  ValidatoUtil.isMobile(s);
        }
    }
}
