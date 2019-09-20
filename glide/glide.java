##if you are using Glide v4 then you have to use RequestOptions for including the more options you want, for example centerCrop(), placeholder(), error(), priority() , diskCacheStrategy().
##So after using RequestOptions your Glide would look like this-

RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.default_avatar)
                    .error(R.drawable.default_avatar)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH);

Glide.with(mContext).load(imgUrl)
                    .apply(options)
                    .into(picThumbnail);
					
##Now you can show error image and placeholder set the disk cache etc.

##GlideApp is also a part of Glide v4. It is used to provide more than one Transformation in Glide v4, using the transforms() method:

GlideApp.with(mContext)
  .load(imgUrl)
  .transforms(new CenterCrop(), new RoundedCorners(20))
  .into(target);
  
##error() and placeholder() using GlideApp-

GlideApp.with(mContext)
            .load(imageUrl)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
            .into(offerImage);