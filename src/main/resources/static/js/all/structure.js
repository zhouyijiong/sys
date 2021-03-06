/**
 * @Author: ZYJ
 * @Date: 2022/04/22
 * @Remark: 父类 Parent
 */
class Parent{
    constructor(type){
        if(type){
            this.check = true;
            this.type = typeof type;
        }
    }
    hashcode(str){
        let hash = 0,i,chr,len;
        if(str.length === 0) return hash;
        for(i=0,len=str.length;i<len;i++){
            chr = str.charCodeAt(i);
            hash = ((hash << 5) - hash) + chr;
            hash |= 0;
        }
        return hash;
    }
    param_check(param){if(this.check && (typeof param !== this.type)) throw 'parameter type must be ' + type;}
    toString(){return null;}
}

/**
 * @Author: ZYJ
 * @Date: 2022/04/22
 * @Remark: 数据结构 HashMap TODO:期望超过8个节点转为二叉树
 */
class HashMap extends Parent{
    constructor(size){
        super('string');
        this.size = 0;
        this.array = new Array(size ? size : 0x10).fill(null);
        this.threshold = this.array.length * 0.75;
    }
    put(k,v){
        let hash = this.hash(k);
        let index = this.getIndex(hash);
        let node = this.array[index];
        if(node == null) this.array[index] = new Node(hash,k,v);
        else{
            let parentNode = null;
            do{
                if(node.hash === hash && node.key === k){
                    node.val = v;
                    return this;
                }
                parentNode = node;
                node = node.next;
            }while(node != null);
            parentNode.next = new Node(hash,k,v);
        }
        if(++this.size > this.threshold) this.expansion();
        return this;
    }
    get(k){
        let hash = this.hash(k);
        let index = this.getIndex(hash);
        let node = this.array[index];
        while(node != null){
            if(node.hash === hash && node.key === k) return node.val;
            node = node.next;
        }
        return null;
    }
    remove(k){
        let hash = this.hash(k);
        let index = this.getIndex(hash);
        let node = this.array[index];
        if(node == null) return null;
        let parentNode = null;
        do{
            if(node.hash === hash && node.key === k){
                let val = node.val;
                if(parentNode == null){
                    this.array[index] = null;
                }else{
                    parentNode.next = node.next;
                    node = null;
                }
                --this.size;
                return val;
            }
            parentNode = node;
            node = node.next;
        }while(node != null);
        return null;
    }
    clear(){
        for(let i=0,len=this.array.length;i<len;++i) this.array[i] = null;
        this.size = 0;
    }
    hash(k){
        super.param_check(k);
        let hash = super.hashcode(k);
        return hash ^ (hash >>> 0x10);
    }
    getIndex(hash){return (this.array.length - 1) & hash}
    expansion(){
        this.threshold = (this.array.length << 1) * 0.75;
        let newMap = new HashMap(this.array.length << 1);
        let node = null,next = null;
        for(let i=0,len=this.array.length;i<len;++i){
            node = this.array[i];
            while(node != null){
                newMap.put(node.key,node.val);
                next = node.next;
                while(next != null){
                    newMap.put(next.key,next.val);
                    next = next.next;
                }
                node = null;
            }
        }
        this.array = newMap.array;
        newMap = null;
    }
    toString(){
        return super.toString();
    }
}
class Node{
    constructor(hash,key,val){
        this.hash = hash;
        this.key = key;
        this.val = val;
        this.next = null;
    }
}

/**
 * @Author: ZYJ
 * @Date: 2022/05/06
 * @Remark: StringBuilder
 */
class StringBuilder extends Parent{
    constructor(size){
        super('string');
        this.array = [size ? size : 0x10];
    }
    add(str){
        this.param_check(str);
        this.array[this.size++] = str;
    }
    hashcode(str){
        return super.hashcode(this.toString());
    }
    toString(){
        return this.array.join('');
    }
}

/**
 * @Author: ZYJ
 * @Date: 2022/04/22
 * @Remark: Ajax
 */
class Ajax{
    constructor(){}
    get(url,formData,success){return this.send('GET',url,'json',formData,success);}
    post(url,formData,success){return this.send('POST',url,'json',formData,success);};
    send(type,url,dataType,formData,success){
        $.ajax({
            type:type,
            url:url,
            dataType:dataType,
            contentType: false,
            processData: false,
            data:formData,
            beforeSend:function(XMLHttpRequest){
                XMLHttpRequest.setRequestHeader('token',localStorage.getItem('token'));
            },success:success
        });
    }
}

/**
 * @Author: ZYJ
 * @Date: 2022/04/25
 * @Remark: K-Value
 */
class Pair extends Parent{
    constructor(key,val){
        super();
        this.key = new Item(key);
        this.val = new Item(val);
    }
    toString(){return '{' + this.key.toString() + ':' + this.val.toString() + '}';}
    hashcode(){return super.hashcode(this.toString());}
}
class Item extends Parent{
    constructor(item){
        super(item);
        this.item = item;
    }
    set(item){
        super.param_check(item);
        this.item = item;
    }
    get(){return this.item;}
    setType(type){super.type = type;}
    toString(){return String(this.item);}
}