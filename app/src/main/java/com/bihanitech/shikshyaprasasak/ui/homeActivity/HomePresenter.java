package com.bihanitech.shikshyaprasasak.ui.homeActivity;

import com.bihanitech.shikshyaprasasak.remote.ApiUtils;
import com.bihanitech.shikshyaprasasak.remote.CDSService;
import com.bihanitech.shikshyaprasasak.remote.RequestHandler;
import com.bihanitech.shikshyaprasasak.repositories.MetaDatabaseRepo;

import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class HomePresenter {

    private MetaDatabaseRepo metaDatabaseRepo;
    private CDSService cdsService;
    private HomeView homeView;

    public HomePresenter(MetaDatabaseRepo metaDatabaseRepo, HomeView homeView) {
        this.metaDatabaseRepo = metaDatabaseRepo;
        this.homeView = homeView;
    }


    public void getStudentsList() {
       // homeView.setUpStudentList(metaDatabaseRepo.getStudentInfo());
    }

//    public void fetchToCheckForSubjectUpdate(String token) {
//        if(cdsService == null)
//            cdsService = ApiUtils.getCDSService();
//
//        Observable<ResponseBody> call = cdsService.getClassTeacherSubject("bearer"+token);
//
//        RequestHandler.asyncTask(call, new RequestHandler.RetroReactiveCallBack<ResponseBody>() {
//            @Override
//            public void onComplete(ResponseBody response) {
//                //Log.v(TAG,response.getUser().getName());
//
//                //Log.v(TAG,response.getUser().getName());
//                //Log.v(TAG,response.getUser().getName());
//                String res ;
//
//                try {
//                    res = response.string();
//
//                    JSONArray array = new JSONArray(res);
//                    if(array.length()==0) {
//                        homeView.proceedAfterUpdate();
//                    }else{
//                        List<ClassSubject> classSubjects = new ArrayList<>();
//
//                        for(int i = 0 ; i < array.length(); i++){
//                            ClassSubject classSubject = new ClassSubject();
//                            classSubject.setClassId(array.getJSONObject(i).getString("CLASSID"));
//                            classSubject.setClassName(array.getJSONObject(i).getString("CLASSNAME"));
//                            classSubject.setSubjectId(array.getJSONObject(i).getString("SUBJECTID"));
//                            classSubject.setSubjectName(array.getJSONObject(i).getString("SUBJECTNAME"));
//                            classSubject.setSectionId(array.getJSONObject(i).getString("SECTIONID"));
//                            classSubject.setSectionName(array.getJSONObject(i).getString("SECTIONNAME"));
//                            //classSubject.setActive(1);
//                            classSubjects.add(classSubject);
//                        }
//                        metaDatabaseRepo.checkAndUpdate(classSubjects);
//                        homeView.proceedAfterUpdate();
//
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                homeView.proceedAfterUpdate();
//            }
//
//            @Override
//            public void onError(Exception e, int code) {
//                //e.printStackTrace();
//
//                e.printStackTrace();
//                homeView.showServerError(0);
//
//            }
//
//            @Override
//            public void onConnectionException(Exception e) {
//                e.printStackTrace();
//                homeView.showNetworkError(0);
//            }
//        });
//
//    }
//
//    public void fetchToCheckForStudentsUpdate(String token) {
//        List<ClassSubject> classInfo = new ArrayList<>();
//        List<ClassSubject> subjects = metaDatabaseRepo.getAllSubjectList();
//        if(subjects.size()!=0){
//            for (ClassSubject subject : subjects) {
//                // add first subject's class info to classInfo list
//                if(classInfo.size()==0){
//                    ClassSubject classSubject = new ClassSubject();
//                    classSubject.setClassId(subject.getClassId());
//                    classSubject.setSectionId(subject.getSectionId());
//                    classInfo.add(classSubject);
//                }else{
//                    // check if the class info already added and add if not
//                    int count = 0;
//                    for (ClassSubject classSubject : classInfo) {
//                        if(classSubject.getClassId().equalsIgnoreCase(subject.getClassId()) &&
//                                classSubject.getSectionId().equalsIgnoreCase(subject.getSectionId())){
//                            count ++;
//                        }
//                    }
//
//                    if( count ==0){
//                        ClassSubject classSubject = new ClassSubject();
//                        classSubject.setClassId(subject.getClassId());
//                        classSubject.setSectionId(subject.getSectionId());
//                        classInfo.add(classSubject);
//                    }
//                }
//            }
//            fetchStudentsForClassFromServer(classInfo,token);
//        }
//    }
//
//    private void fetchStudentsForClassFromServer(final List<ClassSubject> classInfo, final String token) {
//        if(cdsService==null)
//            cdsService = ApiUtils.getCDSService();
//        Observable<StudFrServer> call = null;
//        if(classInfo.get(0).getSectionId().length()==0) {
//            call = cdsService.getStudentListWithoutSection("bearer " + token, classInfo.get(0).getClassId());
//        }else{
//            call = cdsService.getStudentListWithSection("bearer " + token, classInfo.get(0).getClassId(),classInfo.get(0).getSectionId());
//        }
//
//        RequestHandler.asyncTask(call, new RequestHandler.RetroReactiveCallBack<StudFrServer>() {
//            @Override
//            public void onComplete(StudFrServer response) {
//                //Log.v(TAG,response.getUser().getName());
//
//                //Log.v(TAG,response.getUser().getName());
//                //Log.v(TAG,response.getUser().getName());
//                if(response.getRegno().size()!=0) {
//                    List<StudentListItem> studentListItemList = new ArrayList<>();
//                    for (StudentItem studentItem : response.getRegno()) {
//                        StudentListItem studentListItem = new StudentListItem();
//                        studentListItem.setClassId(classInfo.get(0).getClassId());
//                        studentListItem.setSectionId(classInfo.get(0).getSectionId());
//                        studentListItem.setRegNO(studentItem.getRegNo());
//                        studentListItem.setStName(studentItem.getStName());
//                        studentListItem.setRollNo(studentItem.getRollNo());
//                        studentListItemList.add(studentListItem);
//                    }
//                    metaDatabaseRepo.updateStudentList(studentListItemList);
//                    if(classInfo.size()==1) {
//                        homeView.proceedAfterStudentListUpdate();
//                    }else{
//                        classInfo.remove(classInfo.get(0));
//                        fetchStudentsForClassFromServer(classInfo,token);
//                    }
//
//                }else{
//                    if(classInfo.size()==1) {
//                        homeView.proceedAfterStudentListUpdate();
//                    }else{
//                        classInfo.remove(classInfo.get(0));
//                        fetchStudentsForClassFromServer(classInfo,token);
//                    }
//                }
//            }
//
//            @Override
//            public void onError(Exception e, int code) {
//                //e.printStackTrace();
//
//                e.printStackTrace();
//                homeView.showServerError(1);
//
//            }
//
//            @Override
//            public void onConnectionException(Exception e) {
//                e.printStackTrace();
//                homeView.showNetworkError(1);
//            }
//        });
//
//    }
//
//    public List<ClassSubject> getClassInfo() {
//        List<ClassSubject> classInfo = new ArrayList<>();
//        List<ClassSubject> subjects = metaDatabaseRepo.getAllSubjectList();
//        if (subjects.size() != 0) {
//            for (ClassSubject subject : subjects) {
//                // add first subject's class info to classInfo list
//                if (classInfo.size() == 0) {
//                    ClassSubject classSubject = new ClassSubject();
//                    classSubject.setClassId(subject.getClassId());
//                    classSubject.setSectionId(subject.getSectionId());
//                    classInfo.add(classSubject);
//                } else {
//                    // check if the class info already added and add if not
//                    int count = 0;
//                    for (ClassSubject classSubject : classInfo) {
//                        if (classSubject.getClassId().equalsIgnoreCase(subject.getClassId()) &&
//                                classSubject.getSectionId().equalsIgnoreCase(subject.getSectionId())) {
//                            count++;
//                        }
//                    }
//
//                    if (count == 0) {
//                        ClassSubject classSubject = new ClassSubject();
//                        classSubject.setClassId(subject.getClassId());
//                        classSubject.setSectionId(subject.getSectionId());
//                        classInfo.add(classSubject);
//                    }
//                }
//            }
//
//
//        }
//
//        return classInfo;
//    }

}
