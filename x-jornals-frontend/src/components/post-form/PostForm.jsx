import React, { useCallback } from "react";
import { useForm } from "react-hook-form";
import { Button, Input, Select, RTE } from "../index";
import appwriteService from "../../appwrite/config";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";

function PostForm({ post }) {

    const { register, handleSubmit, watch, setValue, control, getValues } = useForm({
        defaultValues: {
            title: post?.title || "",
            slug: post?.$id || "",
            content: post?.content || "",
            status: post?.status || "active",
        }
    })

    const navigate = useNavigate()
    const userData = useSelector((state) => state.auth.userData)

    const submit = async (data) => {
        console.log("inside submit form 1")
        if (post) {
            console.log("inside if block ")
            const file = data.image[0] ? appwriteService.uploadFile(data.image[0]) : null;
            
            if (file) {
                appwriteService.deleteFile(post.featuredImage)
            }
            const dbPost = await appwriteService.updatePost(post.$id, {
                ...data,
                featuredImage: file ? file.$id : undefined,
                
                if(dbPost) {
                    navigate(`/post/${dbPost.$id}`)
                }
            })
        } else {
            //***todo: add logic check if image file available  
            console.log("inside else part")
            const file = await appwriteService.uploadFile(data.image[0]);
            console.log("file:  ", file)
            
            if (file) {
                const fileId = file.$id;
                console.log("fileid:  ", fileId)
                data.featuredImage = fileId
                console.log("hello********** ")
                const dbPost = await appwriteService.createPost({
                    ...data,
                    userId: userData.$id,
                })
                console.log("dbPOst:  ", dbPost)
                if (dbPost) {
                    console.log("333333  ")
                    navigate(`/post/${dbPost.$id}`)
                    console.log("4444444  ")
                }
            }
        }
    }

    const slugTransForm = useCallback((value) => {
        if (value && typeof value === "string") {
            // const slug = value.toLowerCase().replace(/ /g, "-")
            // setValue("slug", slug)
            // return slug

            return value
                .trim()
                .toLowerCase()
                .replace(/[^a-zA-Z\d\s]+/g, "-")
                .replace(/\s/g, "-");
        }
        return "";
    }, [])

    React.useEffect(() => {
        const subscription = watch((value, { name }) => {
            if (name === "title") {
                setValue('slug', slugTransForm(value.title, { shouldValidate: true }))
            }
        });

        return () => {
            subscription.unsubscribe();
        }
    }, [watch, slugTransForm, setValue]);

    return (
        <form onSubmit={handleSubmit(submit)} className="flex flex-wrap">
            <div className="w-2/3 px-2">
                <Input
                    label="Title :"
                    placeholder="Title"
                    className="mb-4"
                    {...register("title", { required: true })}
                />
                <Input
                    label="Slug :"
                    placeholder="Slug"
                    className="mb-4"
                    {...register("slug", { required: true })}
                    onInput={(e) => {
                        setValue("slug", slugTransform(e.currentTarget.value), { shouldValidate: true });
                    }}
                />
                <RTE label="Content :" name="content" control={control} defaultValue={getValues("content")} />
            </div>
            <div className="w-1/3 px-2">
                <Input
                    label="Featured Image :"
                    type="file"
                    className="mb-4"
                    accept="image/png, image/jpg, image/jpeg, image/gif"
                    {...register("image", { required: !post })}
                />
                {post && (
                    <div className="w-full mb-4">
                        <img
                            src={appwriteService.getFilePreview(post.featuredImage)}
                            alt={post.title}
                            className="rounded-lg"
                        />
                    </div>
                )}
                <Select
                    options={["active", "inactive"]}
                    label="Status"
                    className="mb-4"
                    {...register("status", { required: true })}
                />
                <Button type="submit" bgColor={post ? "bg-green-500" : undefined} className="w-full">
                    {post ? "Update" : "Submit"}
                </Button>
            </div>
        </form>
    );
}

export default PostForm