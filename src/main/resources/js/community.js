/**
 * 回复
 */

function comment2target(targetId,type,content) {
    if(!content){
        alert("请输入回复内容！");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify ({
            "parentId":targetId,
            "type":type,
            "content":content
        }),
        success: function (response) {
            if(response.code == 200){
                window.location.reload();
            }else{
                if(response.code == 2003){
                    var isAccepted = confirm(response.message);
                    if(isAccepted == true){
                        var a = $("#redirect").val();
                        window.open("https://github.com/login/oauth/authorize?client_id=1cac5f8fd3854fe3fd1a&redirect_uri="+a+"&scope=user&state=1");
                        localStorage.setItem("isAccepted", true);
                        window.location.reload();
                    }
                }else{

                    alert(response.message);
                }
            }

        },
        dataType: "json"
    });
}


function post() {
    var question_id = $("#question_id").val();
    var  comment_content =  $("#comment_content").val();
    comment2target(question_id,1,comment_content);
}

function comment(e) {

    var id = e.getAttribute("data-id");
    var input = $("#input-" + id).val();
    comment2target(id,2,input);
}

/**
 * 评论
 */
 function collapse_comment(e){

     var id = e.getAttribute("data-id");
     var comment = $("#comment-" + id);
     var collapse = e.getAttribute("collapse");

     if(collapse){
         //关闭二级评论
         comment.removeClass("in");
         e.removeAttribute("collapse");
     }else{
         var collapse = $("#comment-" + id);
         var len = collapse.children().length;
         console.log(len);
         if(len == 1){
             $.getJSON( "/comment/"+id, function( data ) {
                 $.each( data.data.reverse(), function( key, val ) {

                     var mediaLeft = $("<div/>",{
                         "class":"media-left"
                     }).append($("<img/>",{
                         "class":"media-object img-circle ",
                         "src":val.user.avatarUrl
                     }));

                     var mediaBody = $("<div/>",{
                         "class":"media-body"
                     }).append($("<h5/>",{
                         "class":"media-heading",
                         "html":val.user.name
                     })).append($("<div/>",{
                         "html":val.content
                     })).append($("<div/>",{
                         "class":"menu"
                     }).append($("<span/>",{
                         "class":"pull-right",
                         "html":moment(val.gmtCreate).format('YYYY-MM-DD')
                     })));

                     var media = $("<div/>",{
                         "class":"media"
                     }).append(mediaLeft).append(mediaBody);

                     var comments = $("<div/>",{
                         "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                     }).append(media);

                     collapse.prepend(comments);

                 });
                 //打开二级评论
                 comment.addClass("in");
                 e.setAttribute("collapse","in");
             });
         }else{
             //打开二级评论
             comment.addClass("in");
             e.setAttribute("collapse","in");
         }
     }

}

function tagShow() {

     var tag = $("#select-tag");
     tag.show();

}
function tagHidden() {

    var tag = $("#select-tag");
    tag.hide();

}

function selectTag(e) {


    var comment = $("#tag").val();
    var tagNames = e.getAttribute("data-tag");
    if(comment.indexOf(tagNames) == -1){
        if(comment){
            $("#tag").val(comment + "," + tagNames);
        }else{
            $("#tag").val(tagNames);
        }
    }




}