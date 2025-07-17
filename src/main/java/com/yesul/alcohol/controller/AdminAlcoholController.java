package com.yesul.alcohol.controller;

import com.yesul.alcohol.model.dto.AlcoholDetailDto;
import com.yesul.alcohol.model.dto.AlcoholDto;
import com.yesul.alcohol.service.AlcoholService;
import com.yesul.exception.handler.RegistrationFailedException;
import com.yesul.utill.ImageUpload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/alcohols")
public class AdminAlcoholController {

    private final AlcoholService alcoholService;
    private final ImageUpload imageUpload;

    @GetMapping
    public String alcoholMgmtPage(@PageableDefault(size = 12) Pageable pageable, Model model) {
        Page<AlcoholDto> alcoholListPageable = alcoholService.getAlcoholList(pageable);
        model.addAttribute("alcoholList", alcoholListPageable);
        return "/admin/alcohol/list";
    }

    @GetMapping("/{id}")
    public String getAlcoholDetail(@PathVariable Long id, Model model) {
        AlcoholDetailDto alcohol = alcoholService.getAlcoholDetailById(id);

        Map<String, Integer> tasteLevels = new LinkedHashMap<>();
        tasteLevels.put("단맛", alcohol.getSweetnessLevel());
        tasteLevels.put("산미", alcohol.getAcidityLevel());
        tasteLevels.put("바디감", alcohol.getBodyLevel());
        tasteLevels.put("향", alcohol.getAromaLevel());
        tasteLevels.put("떫은맛", alcohol.getTanninLevel());
        tasteLevels.put("여운", alcohol.getFinishLevel());
        tasteLevels.put("탄산감", alcohol.getSparklingLevel());

        model.addAttribute("alcohol", alcohol);
        model.addAttribute("tasteLevels", tasteLevels);
        return "admin/alcohol/detail";
    }

    @GetMapping("/regist")
    public String registPage() {
        return "admin/alcohol/regist";
    }

    @PostMapping("/regist")
    public String alcoholRegist(@ModelAttribute AlcoholDetailDto alcoholDetailDto, MultipartFile imageFile) {

        String domain = "alcohol";
        String url = imageUpload.uploadAndGetUrl(domain, imageFile);
        alcoholDetailDto.setImage(url);
        alcoholService.registAlcohol(alcoholDetailDto);

        return "redirect:/admin/alcohols";
    }
}
