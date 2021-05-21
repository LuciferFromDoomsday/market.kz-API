package com.example.salamnura.todolist.rest;

import com.example.salamnura.todolist.entities.*;
import com.example.salamnura.todolist.repositories.AdsRepository;
import com.example.salamnura.todolist.repositories.UsersRepository;
import com.example.salamnura.todolist.services.AdsService;
import com.example.salamnura.todolist.services.CardService;
import com.example.salamnura.todolist.services.impl.AdsServiceImpl;
import com.example.salamnura.todolist.services.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "api/market")
class MainRestController {



    @Autowired
    private UsersRepository userRepository;


    @Autowired
    private BCryptPasswordEncoder encoder;;


    @Autowired
    private AdsService adsService;

    @Value("${file.avatar.uploadPath}")
    private String uploadPath;

    @Value("file.itemPicture.defaultPicture")
    private String defaultItemPicture;

    @Value("${file.itemPictures.viewPath}")
    private String itemPictureViewPath;

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }




    @GetMapping(value = "/allAds")
    public ResponseEntity<?> getAllAds(){
        List<Ads> ads =adsService.getAllAds();
        return new ResponseEntity<>(ads, HttpStatus.OK);
    }

    @GetMapping(value = "/allAdsWithFilter")
    public ResponseEntity<?> getallAdsWithFilter(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int priceMoreThan,
            @RequestParam(defaultValue = "0") int priceLessThan,
            @RequestParam(defaultValue = "3") int category_id,
            @RequestParam(defaultValue = "3") int city
    ){
        List<Ads> ads =adsService.getAllAds();
        return new ResponseEntity<>(ads, HttpStatus.OK);
    }



    @GetMapping(value = "/ads/{id}")
    public ResponseEntity<?> getAdsdById(@PathVariable Long id) {
        Ads ads = adsService.getAds(id);
        System.out.println(ads.getId());
        return new ResponseEntity<>(ads, HttpStatus.OK);

    }

        @PreAuthorize("isAuthenticated()")
    @PostMapping("/addAds")
    public ResponseEntity<?> addAds(@RequestBody Ads ads) {
        Ads newads = new Ads();
            System.out.println(ads.getAdType().getName());
        long date=System.currentTimeMillis();
        Date addeddate=new Date(date);
        newads.setAddedDate(addeddate);
        newads.setName(ads.getName());
        newads.setAdress(ads.getAdress());
        newads.setCity(ads.getCity());
        newads.setBrand(ads.getBrand());
        newads.setDescription(ads.getDescription());
        newads.setIsNew(ads.getIsNew());
        newads.setAdType(ads.getAdType());
        newads.setPrice(ads.getPrice());
        newads.setUser(ads.getUser());
        Ads ads1 = adsService.addAds(newads);
        return new ResponseEntity<>(ads1.getId(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/addImages/{id}")
    public ResponseEntity<?> addImages(@ModelAttribute MultipartFile file , @PathVariable String id) {



            if (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")) {
                try {

                    Image image = new Image();
                    image.setAds(adsService.getAds(Long.parseLong(id.substring(0, id.length() - 1))));

                    image = adsService.saveImage(image);

                    String picName = DigestUtils.sha1Hex("ad" + id + "imageId"  + image.getId() +   "!_Picture");

                    byte[] bytes = file.getBytes();

                    Path path = Paths.get(uploadPath + picName + ".jpg");
                    Files.write(path, bytes);

                    image.setUrl(picName);

                        adsService.saveImage(image);


                } catch (Exception e) {
                    e.printStackTrace();
                }

        }

        return new ResponseEntity<>("Uploaded pasany", HttpStatus.OK);
    }

    @GetMapping(value = "/viewAdsPictures/{url}" , produces = {MediaType.IMAGE_JPEG_VALUE} )
    public @ResponseBody byte[] viewAdsPictures(@PathVariable(name = "url") String url ) throws IOException {

        System.out.println(url);
        String pictureUrl = defaultItemPicture;

        if(url != null){
            pictureUrl = itemPictureViewPath  + url + ".jpg";
        }
        System.out.println(pictureUrl);
        InputStream in ;
        try{
            ClassPathResource resource = new ClassPathResource(pictureUrl);
            in = resource.getInputStream();
        }
        catch (Exception e){
            ClassPathResource resource = new ClassPathResource(defaultItemPicture);
            in = resource.getInputStream();
        }
        return IOUtils.toByteArray(in);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/updateAds")
    public ResponseEntity<?> updateAds(@RequestBody Ads ads) {
        System.out.println(ads.getName());

        Ads newads = adsService.saveAds(ads);
        return new ResponseEntity<>(newads, HttpStatus.OK);
    }

        @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/deleteAds/{id}")
    public ResponseEntity<?> deleteCard(@PathVariable Long id ) {
            try {

                List<Image> images = adsService.getAdsImage(id);

                for (Image image:images
                     ) {
                    adsService.deleteImage(image);
                }



                Ads ads = adsService.getAds(id);
                System.out.println(ads + "bbb");
                adsService.deleteAds(ads);
                return new ResponseEntity<>(ads, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }


    @GetMapping(value = "/allAdsByAdsTypeId/{id}")
    public ResponseEntity<?> getAllAdsById(@PathVariable("id") long id){
        Long ids= id;
        List<Ads> ads = adsService.getAdsByAdTypeId(ids);
        return new ResponseEntity<>(ads, HttpStatus.OK);
    }
    @GetMapping(value = "/allAdsByUserId/{id}")
    public ResponseEntity<?> getAllAdsByuserId(@PathVariable("id") long id){
        Long ids= id;
        List<Ads> ads = adsService.getAdsByUserId(id);
        return new ResponseEntity<>(ads, HttpStatus.OK);
    }

    @GetMapping(value = "/allAdsTypes")
    public ResponseEntity<?> getAllAdsTypes(){
        List<AdType> adTypes =adsService.getAllAdTypes();
        return new ResponseEntity<>(adTypes, HttpStatus.OK);
    }

    @GetMapping(value = "/getImage/{id}")
    public ResponseEntity<?> getImages(@PathVariable("id") long id){
        List<Image> images =adsService.getAdsImage(id);

        System.out.println(images.size());

        return new ResponseEntity<>(images, HttpStatus.OK);
    }





//
//
//    @PreAuthorize("isAuthenticated()")
//    @GetMapping(value = "/allCards")
//    public ResponseEntity<?> getAllCards(){
//        List<Card> cards=cardService.getAllCards();
//        return new ResponseEntity<>(cards, HttpStatus.OK);
//    }
//    @PreAuthorize("isAuthenticated()")
//    @GetMapping(value = "/card/{id}")
//    public ResponseEntity<?> getCardById(@PathVariable Long id) {
//        Card cards=cardService.getCard(id);
//        System.out.println(cards.getId());
//        if (cards!=null) {
//            return new ResponseEntity<>(cards, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//    }
//    @PreAuthorize("isAuthenticated()")
//    @GetMapping(value = "/searchByName/{name}")
//    public ResponseEntity<?> getCardByName(@PathVariable String name) {
//        List<Card> cards=cardService.getAllCardsByName(name);
//        System.out.println(cards);
//        if (cards!=null) {
//            return new ResponseEntity<>(cards, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//    }
//    @PreAuthorize("isAuthenticated()")
//    @PostMapping("/addCard")
//    public ResponseEntity<?> addCard(@RequestBody Card cards) {
//        Card newcard=new Card();
//        long date=System.currentTimeMillis();
//        Date addeddate=new Date(date);
//        newcard.setAddedDate(addeddate);
//        newcard.setName(cards.getName());
//        Card card=cardService.addCard(newcard);
//        return new ResponseEntity<>(card, HttpStatus.OK);
//    }
//
//    @PreAuthorize("isAuthenticated()")
//    @PutMapping("/updateCard")
//    public ResponseEntity<?> updateCard(@RequestBody Card cards) {
//        System.out.println(cards.getName());
//
//        Card card=cardService.saveCard(cards);
//        return new ResponseEntity<>(card, HttpStatus.OK);
//    }
//    @PreAuthorize("isAuthenticated()")
//    @DeleteMapping("/deleteCard/{id}")
//    public ResponseEntity<?> deleteCard(@PathVariable Long id ) {
//        try {
//
//            Card cards1=cardService.getCard(id);
//            System.out.println(cards1+"bbb");
//            cardService.deleteCard(cards1);
//            return new ResponseEntity<>(cards1,HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//
//    }
//    @PreAuthorize("isAuthenticated()")
//    @GetMapping(value = "/allTaskByCardId/{id}")
//    public ResponseEntity<?> getAllTaskById(@PathVariable("id") long id){
//        Long ids= id;
//        List<CardTasks> cards=cardService.getAllTasksByCard(ids);
//        return new ResponseEntity<>(cards, HttpStatus.OK);
//    }
//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/task/{id}")
//    public ResponseEntity<?> getTaskById(@PathVariable("id") long id) {
//        CardTasks task=cardService.getTask(id);
//        return new ResponseEntity<>(task, HttpStatus.OK);
//    }
//    @PreAuthorize("isAuthenticated()")
//    @PostMapping("/addTask")
//    public ResponseEntity<?> addTask(@RequestBody CardTasks task) {
//        CardTasks newtask=new CardTasks();
//        long date=System.currentTimeMillis();
//        Date addeddate=new Date(date);
//        newtask.setAddedDate(addeddate);
//        newtask.setTaskText(task.getTaskText());
//        newtask.setDone(false);
//        newtask.setCard(task.getCard());
//
//        System.out.println(task.getCard()+" "+task.getTaskText());
//        CardTasks task_add=cardService.addTask(newtask);
//        return new ResponseEntity<>(task_add, HttpStatus.OK);
//    }
//    @PreAuthorize("isAuthenticated()")
//    @PutMapping("/updateTask")
//    public ResponseEntity<?> updateTask(@RequestBody CardTasks task) {
//        CardTasks card=cardService.saveTask(task);
//        return new ResponseEntity<>(card, HttpStatus.OK);
//    }
//    @PreAuthorize("isAuthenticated()")
//    @DeleteMapping("/deleteTask/{id}")
//    public ResponseEntity<?> deleteTask(@PathVariable Long id ) {
//        try {
//
//            CardTasks task=cardService.getTask(id);
//            System.out.println(task+"bbb");
//            cardService.deleteTask(task);
//            return new ResponseEntity<>(task,HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/updateFullname")
    public ResponseEntity<?> updateFullname(@RequestBody Users users) {
        Users users1= userRepository.findById(users.getId()).get();
        users1.setFullName(users.getFullName());
        userRepository.save(users1);
        return new ResponseEntity<>(users1,HttpStatus.OK);
    }

    private  Users getUserData(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
        Users users=userRepository.findByEmail(userDetails.getEmail()).get();
        return users;
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/updatePassword")
    public String updatePassword(@RequestBody ChangePassword changePassword) {
        Users users=getUserData();
        System.out.println(users.getPassword()+"----------------"+users.getEmail());
        if(encoder.matches(changePassword.getOldpassword(),users.getPassword())){

            users.setPassword(encoder.encode(changePassword.getNewpassword()) );
            userRepository.save(users);
            return "success";
        }
        else {
            return "are not equal";
        }
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/getPassword")
    public String getPassword(@PathVariable("id") long id) {
        String users1= userRepository.findById(id).get().getPassword();
        System.out.println(users1);
        return users1;
    }
}
