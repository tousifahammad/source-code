url : https://stackoverflow.com/questions/13784088/setting-popupmenu-menu-items-programmatically        


        tv_quantity.setOnClickListener(v -> {
            Animator.buttonAnim(activity, v);

            PopupMenu popup = new PopupMenu(activity, v);
            if (price_list != null && price_list.size() > 0) {
                for (int i = 0; i < price_list.size(); i++) {
                    popup.getMenu().add(Menu.NONE, i, i, price_list.get(i));
                }
            }
            popup.setOnMenuItemClickListener(item -> {
                viewHolder.tv_quantity.setText(item.getTitle());
                return true;
            });
            popup.show();
        });

