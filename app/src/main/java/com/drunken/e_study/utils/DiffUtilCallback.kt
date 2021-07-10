import androidx.recyclerview.widget.DiffUtil
import com.drunken.e_study.models.Course

class ItemDiffUtilCallback : DiffUtil.ItemCallback<Course>(){
    override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem == newItem
    }

}