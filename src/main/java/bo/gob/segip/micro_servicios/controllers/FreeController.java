//package bo.gob.segip.micro_servicios.controllers;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.session.SessionInformation;
//import org.springframework.security.core.session.SessionRegistry;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("free")
//@RequiredArgsConstructor
//public class FreeController {
//
//    private final SessionRegistry sessionRegistry;
//
//    @GetMapping("/route")
//    public String freeMessage() {
//        return "Soy libre";
//    }
//
//    @GetMapping("/session")
//    public ResponseEntity<?> getDetailsSession() {
//
//        String sessionId = "";
//        User userObject = null;
//
//        List<Object> sessions = sessionRegistry.getAllPrincipals();
//
//        for (Object session : sessions) {
//            if (session instanceof User) {
//                User user = (User) session;
//                userObject = user;
//
//                List<SessionInformation> sessionInformationList = sessionRegistry.getAllSessions(user, false);
//
//                for (SessionInformation sessionInformation : sessionInformationList) {
//                    sessionId = sessionInformation.getSessionId();
//                }
//            }
//        }
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("response", "Hello world!");
//        response.put("sessionId", sessionId);
//        response.put("session", userObject);
//
//        return ResponseEntity.ok(response);
//    }
//
//}
