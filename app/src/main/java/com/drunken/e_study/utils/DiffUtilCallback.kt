import androidx.recyclerview.widget.DiffUtil
import com.drunken.e_study.dto.Course

class ItemDiffUtilCallback : DiffUtil.ItemCallback<Course>(){
    override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem == newItem
    }
}

class StringDiffUtilCallback : DiffUtil.ItemCallback<String>(){
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}

class MapDiffUtilCallback : DiffUtil.ItemCallback<Map<String, String>?>(){
    override fun areItemsTheSame(
        oldItem: Map<String, String>,
        newItem: Map<String, String>
    ): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(
        oldItem: Map<String, String>,
        newItem: Map<String, String>
    ): Boolean {
        return oldItem == newItem
    }


}