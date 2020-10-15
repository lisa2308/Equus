//package com.example.equus;
//
//public class Users {
//
//    private static final String COLLECTION_NAME = "users";
//
//    // --- COLLECTION REFERENCE ---
//
//    public static CollectionReference getUsersCollection(){
//        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
//    }
//
//    // --- CREATE ---
//
//    public static Task<Void> createUser(String id, String name, String email, String photo) {
//        User userToCreate = new User(name, email, photo);
//        return getUsersCollection().document(id).set(userToCreate);
//    }
//
//    // --- GET ---
//    public static Task<DocumentSnapshot> getUser(String id){
//        return UserHelper.getUsersCollection().document(id).get();
//    }
//
//    // --- UPDATE ---
//    public static Task<Void> updateRestaurantId(String id, String restaurantId) {
//        return UserHelper.getUsersCollection().document(id).update("restaurantId", restaurantId);
//    }
//
//    public static Task<Void> updateRestaurantName(String id, String restaurantName) {
//        return UserHelper.getUsersCollection().document(id).update("restaurantName", restaurantName);
//    }
//}
//
