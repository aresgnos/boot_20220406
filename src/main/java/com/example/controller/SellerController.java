package com.example.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.dto.ItemDTO;
import com.example.entity.BuyProjection;
import com.example.entity.ItemEntity;
import com.example.entity.MemberEntity;
import com.example.mapper.ItemMapper;
import com.example.repository.BuyRepository;
import com.example.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/seller")
public class SellerController {

    @Autowired
    ItemMapper iMapper; // MYBATIS

    @Autowired
    BuyRepository buyRepository; // JPA + HIBERNATE

    @Autowired
    ItemService iService;

    // int PAGECNT = 10
    // global.properties에 설정되어있는 값을 가져오는 것
    @Value("${board.page.count}")
    int PAGECNT;

    // 일괄 삭제, 수정
    @GetMapping(value = "/deleteupdatebatch")
    public String deleteupdateBatchGET(
            Model model,
            @RequestParam(name = "btn") String btn,
            @RequestParam(name = "no") Long[] no) {

        System.out.println(btn);
        System.out.println(no[0]);
        if (btn.equals("일괄수정")) {
            List<ItemEntity> list = iService.selectItemEntityIn(no);
            model.addAttribute("list", list);

            return "update_item_batch";

        } else if (btn.equals("일괄삭제")) {
            iService.deleteItemBatch(no);

        }
        return "redirect:/seller/home";
    }

    // 일괄 수정
    @PostMapping(value = "/updateitembatch")
    public String updateitemBatchPOST(
            @RequestParam(name = "icode") Long[] icode,
            @RequestParam(name = "iname") String[] iname,
            @RequestParam(name = "icontent") String[] icontent,
            @RequestParam(name = "iprice") Long[] iprice,
            @RequestParam(name = "iquantity") Long[] iquantity) {

        List<ItemEntity> list = new ArrayList<>();
        for (int i = 0; i < iname.length; i++) {
            ItemEntity item = new ItemEntity();
            item.setIcode(icode[i]);
            item.setIname(iname[i]);
            item.setIcontent(icontent[i]);
            item.setIprice(iprice[i]);
            item.setIquantity(iquantity[i]);

            list.add(item);

        }

        iService.updateItemBatch(list);
        return "redirect:/seller/home";
    }

    // 일괄 등록
    @GetMapping(value = "/insertitembatch")
    public String insertBatchGET() {
        return "insert_item_batch";
    }

    @PostMapping(value = "/insertitembatch")
    public String insertBatchPOST(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "iname") String[] iname,
            @RequestParam(name = "icontent") String[] icontent,
            @RequestParam(name = "iprice") Long[] iprice,
            @RequestParam(name = "iquantity") Long[] iquantity,
            @RequestParam(name = "timage") MultipartFile[] iimage) throws IOException {

        List<ItemEntity> list = new ArrayList<>();
        for (int i = 0; i < iname.length; i++) {
            System.out.println(iname[i]);
            System.out.println(icontent[i]);
            System.out.println(iprice[i]);
            System.out.println(iquantity[i]);
            System.out.println(iimage[i].getOriginalFilename());

            ItemEntity item = new ItemEntity();
            item.setIname(iname[i]);
            item.setIcontent(icontent[i]);
            item.setIprice(iprice[i]);
            item.setIquantity(iquantity[i]);
            item.setIimage(iimage[i].getBytes());
            item.setIimagename(iimage[i].getOriginalFilename());
            item.setIimagesize(iimage[i].getSize());
            item.setIimagetype(iimage[i].getContentType());

            MemberEntity member = new MemberEntity();
            member.setUemail(user.getUsername());
            item.setMember(member);

            list.add(item);
        }

        iService.insertItemBatch(list);

        return "redirect:/seller/home";
    }

    // 물품 등록
    @GetMapping(value = "/insertitem")
    public String insertitemGET() {
        return "insertitem";
    }

    @PostMapping(value = "/insertitem")
    public String insertitemPOST(
            @AuthenticationPrincipal User user,
            @ModelAttribute ItemDTO item,
            @RequestParam(name = "timage") MultipartFile file) throws IOException {

        System.out.println(item.toString()); // 아이템은 잘 오는지
        System.out.println(file.getOriginalFilename()); // 파일은 파일이름으로 확인

        if (user != null) { // 로그인이 유무 확인(로그인 되었을 때)
            item.setIimage(file.getBytes()); // 오류나기 때문에 오류 던져
            item.setIimagename(file.getOriginalFilename());
            item.setIimagesize(file.getSize());
            item.setIimagetype(file.getContentType());

            // 누가 올린건지
            item.setUemail(user.getUsername());

            iMapper.insertItemOne(item);

            return "redirect:/seller/home";
        }
        // 로그인이 되지 않았을 경우
        return "redirect:/member/login";
    }

    // 물품 수정
    @GetMapping(value = "/updateitem")
    public String updateitemGET(
            Model model,
            @AuthenticationPrincipal User user,
            @RequestParam(value = "code") long code) {
        if (user != null) { // 로그인 되었을 때
            ItemDTO item = iMapper.selectItemOne(code);
            model.addAttribute("item", item);
            return "updateitem";
        }
        return "redirect:/member/login";
    }

    @PostMapping(value = "/updateitem")
    public String updateitemPOST(
            Model model,
            @AuthenticationPrincipal User user,
            @ModelAttribute ItemDTO item,
            @RequestParam(name = "timage") MultipartFile file) throws IOException {
        System.out.println(item.toString());
        if (user != null) { // 로그인 되었다면
            if (!file.isEmpty()) { // 이미지 첨부되었다면
                item.setIimage(file.getBytes());
                item.setIimagename(file.getOriginalFilename());
                item.setIimagesize(file.getSize());
                item.setIimagetype(file.getContentType());
            }
            item.setUemail((user.getUsername()));
            iMapper.updateItemOne(item);

            // 알림창이 필요시
            model.addAttribute("msg", "물품변경완료");
            model.addAttribute("url", "/seller/home");
            return "alert";

            // 알림창이 필요없으면
            // return "redirect:/seller/home";
        }
        return "redirect:/member/login";
    }

    // 물품 삭제
    @PostMapping(value = "/deleteitem")
    public String deleteitemPOST(
            @AuthenticationPrincipal User user,
            @RequestParam(value = "code") long code) {
        if (user != null) { // 로그인 되었을 때
            System.out.println(code);
            int ret = iMapper.deleteItemOne(user.getUsername(), code);
            if (ret == 1) {
                // DELETE FROM ITEM WHERE ICODE=#{code}
                return "redirect:/seller/home";
            }
            return "redirect:/seller/home";

        }
        // 로그인 되지 않았을 경우
        return "redirect:/member/login";
    }

    // 127.0.0.1:9090/ROOT/seller
    // 127.0.0.1:9090/ROOT/seller/home
    @GetMapping(value = { "/", "/home" })
    public String sellerGET(
            Model model,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "txt", defaultValue = "") String txt,
            @AuthenticationPrincipal User user) { // 로그인 됐는지 안됐는지
        if (user != null) {

            // 1일 때 -> 1, 10
            // 2일 때 -> 11, 20

            // 1 -> 1,5
            // 2 -> 6, 10
            // 목록
            List<ItemDTO> list = iMapper.selectItemList(user.getUsername(), txt, (page * PAGECNT) - (PAGECNT - 1),
                    page * PAGECNT);
            model.addAttribute("list", list);

            // 페이지네이션 개수
            long cnt = iMapper.selectItemCount(user.getUsername(), txt);
            model.addAttribute("pages", (cnt - 1) / PAGECNT + 1);

            // 주문내역
            List<Long> list1 = new ArrayList<>();
            for (ItemDTO item : list) {
                list1.add(item.getIcode());
            }

            List<BuyProjection> list2 = buyRepository.findByItem_icodeIn(list1);
            model.addAttribute("list2", list2);

            return "seller";

        }
        return "redirect:/member/login";
    }
}
