class RightClickMenu{
    constructor(name) {
        this.name = name;
        this.Div = document.getElementById(name+"Div");
        this.menu = document.getElementById(name+"Menu")
        this.selectedMenu=null;
        this.selectedMenu=null;
       this.listener = this.Div.addEventListener("contextmenu",this.openRightClickMenu);
        this.SelElemId=null;
        this.menuHeight = this.menu.offsetHeight - parseInt(getComputedStyle(this.menu)['paddingTop']) - parseInt(getComputedStyle(this.menu)['paddingBottom'])
    }

    setListener() {
        groupDiv.addEventListener("mouseover", function (e) {
            // highlight the mouseenter target

            if ((e.target.parentElement.id == "group") && (e.target.id != "")) {
                if (this.SelElemId != e.target.id) {
                    if (this.selectedMenu != null) {
                        this.selectedMenu.classList.remove('active');
                    }
                    this.SelElemId = e.target.id;
                    this.selectedMenu = document.getElementById(e.target.id);
                    this.closeRightClickMenu();
                }

                // console.log(groupSelElemId);
                // console.log(e.target.parentElement.id);
                // console.log("---------");
            }

        });
    }

    closeRightClickMenu(){

        this.selectedMenu.classList.remove('active');
        console.log('closeMenu');
        this.menu.style.height = '0';
        this.menu.classList.remove('right-click-is-active');
    }

    openRightClickMenu(e){
        this.selectedMenu.classList.add('active');
        console.log('openMenu '+menuHeight);
        e.preventDefault();

        //menu.style.left = (e.clientX-500)+'px';
        this.menu.style.left = (250)+'px';
        this.menu.style.top = (e.clientY-80)+'px';
        //menu.style.top = (0)+'px';
        this.menu.style.height = menuHeight+'px';
        //把css class 加给menu
        this.menu.classList.add('right-click-is-active');

        return false;
    }
}